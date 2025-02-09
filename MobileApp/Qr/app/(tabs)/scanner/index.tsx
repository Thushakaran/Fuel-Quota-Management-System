import {
  View,
  Text,
  StyleSheet,
  SafeAreaView,
  Pressable,
  TextInput,
  Button,
  Alert,
  Keyboard,
  TouchableWithoutFeedback,
  Platform,
  StatusBar,
  AppState,
} from "react-native";
import { useRouter } from "expo-router";
import { useState, useRef, useEffect } from "react";
import AsyncStorage from "@react-native-async-storage/async-storage";
import { useCameraPermissions, CameraView } from "expo-camera";
import axios from "axios";

export default function Scanner() {
  const qrLock = useRef(false);
  const router = useRouter();
  const appState = useRef(AppState.currentState);

  const [scannedData, setScannedData] = useState("");
  const [liters, setLiters] = useState("");
  const [fuelStationId, setFuelStationId] = useState("");

  const [permission, requestPermission] = useCameraPermissions();
  const isPermissionGranted = Boolean(permission?.granted);

  useEffect(() => {
    const subscription = AppState.addEventListener("change", (nextAppState) => {
      if (
        appState.current.match(/inactive|background/) &&
        nextAppState === "active"
      ) {
        qrLock.current = false;
      }
      appState.current = nextAppState;
    });
  
    const fetchStationId = async () => {
      try {
        const storedStationId = await AsyncStorage.getItem("stationId");
        if (storedStationId) {
          setFuelStationId(storedStationId);
        }
      } catch (error) {
        console.error("Error retrieving stationId:", error);
      }
    };
  
    fetchStationId(); 
  
    return () => {
      subscription.remove();
    };
  }, []);
  

  const handleRequestPermission = async () => {
    if (!isPermissionGranted) {
      const response = await requestPermission();
      if (!response.granted) {
        Alert.alert(
          "Permission Denied",
          "Camera access is required to scan QR codes. Please enable it in your settings."
        );
      }
    }
  };

  const handleBarcodeScanned = async ({ data }: { data: string }) => {
    if (data && !qrLock.current) {
      qrLock.current = true;
     // console.log(data.replace("QR Code ID:",""))
      setScannedData(data.replace("QR Code ID:",""));
    }
  };

  const handleConfirm = async () => {
  if (!scannedData || !liters || !fuelStationId) {
    Alert.alert("Missing Fields", "Please fill in all fields.");
    return;
  }

  console.log("Confirming fuel update...");
  console.log("Scanned QR Code ID:", scannedData);
  console.log("Liters:", liters);
  console.log("Fuel Station ID:", fuelStationId);

  try {
    const response = await axios.post(
      "http://172.19.96.152:8080/api/transactions/updateFuelQuota",
      {
        qrCodeId: scannedData,
        amount: liters,
        stationId: fuelStationId,
      }
    );

    console.log("API Response:", response.data);

    if (response.status === 200) {
      Alert.alert("Success", "Fuel updated successfully!");
      setLiters("");
      setFuelStationId("");
      setScannedData("");
      qrLock.current = false;
    } else {
      Alert.alert("Error", response.data?.message || "Something went wrong.");
    }
  } catch (error: any) {
    console.error("API Error:", error);

    if (error.response) {
      console.error("Server Response:", error.response.data);
      Alert.alert("API Error", error.response.data || "Failed to update fuel quota.");
    } else if (error.request) {
      console.error("No Response Received:", error.request);
      Alert.alert("Network Error", "No response from the server. Check your connection.");
    } else {
      console.error("Error Setting Up Request:", error.message);
      Alert.alert("Request Error", "Something went wrong. Please try again.");
    }
  }
};


  const handleLogout = async () => {
    try {
      await AsyncStorage.removeItem("userToken");
      router.replace("/login"); // Ensure correct routing
    } catch (error) {
      console.error("Logout Error:", error);
      Alert.alert("Logout Failed", "Please try again.");
    }
  };

  return (
    <TouchableWithoutFeedback onPress={Keyboard.dismiss}>
      <SafeAreaView style={styles.container}>
        <Pressable onPress={handleLogout} style={styles.logoutButton}>
          <Text style={styles.logoutText}>Logout</Text>
        </Pressable>

        <View style={styles.buttonContainer}>
          <Pressable onPress={handleRequestPermission} style={styles.permissionButton}>
            <Text style={styles.buttonText}>
              {isPermissionGranted ? "Permission Granted" : "Request Permissions"}
            </Text>
          </Pressable>
        </View>

        {Platform.OS === "android" ? <StatusBar hidden /> : null}
        {isPermissionGranted && (
          <CameraView
            style={styles.camera}
            facing="back"
            onBarcodeScanned={handleBarcodeScanned}
          />
        )}

        {scannedData && (
          <View style={styles.overlay}>
            <Text style={styles.label}>Enter Liters Pumped</Text>
            <TextInput
              style={styles.input}
              keyboardType="numeric"
              placeholder="Enter liters"
              value={liters}
              onChangeText={setLiters}
            />
           
            <Button title="Confirm" onPress={handleConfirm} />
          </View>
        )}
      </SafeAreaView>
    </TouchableWithoutFeedback>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: "black",
  },
  camera: {
    width: "100%",
    height: "50%",
  },
  buttonContainer: {
    marginVertical: 20,
  },
  permissionButton: {
    padding: 10,
    backgroundColor: "#0E7AFE",
    borderRadius: 5,
  },
  buttonText: {
    color: "white",
    fontSize: 18,
  },
  overlay: {
    backgroundColor: "white",
    padding: 20,
    borderRadius: 10,
    position: "absolute",
    bottom: 20,
    width: "90%",
  },
  label: {
    fontSize: 16,
    marginBottom: 5,
  },
  input: {
    borderWidth: 1,
    borderColor: "gray",
    padding: 10,
    borderRadius: 5,
    marginBottom: 10,
  },
  logoutButton: {
    position: "absolute",
    top: 20,
    right: 20,
    padding: 10,
    backgroundColor: "red",
    borderRadius: 5,
  },
  logoutText: {
    color: "white",
    fontSize: 16,
  },
});
