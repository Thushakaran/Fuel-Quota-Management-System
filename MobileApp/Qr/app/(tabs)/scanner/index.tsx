import { CameraView } from "expo-camera";
import { Stack } from "expo-router";
import {
  AppState,
  Linking,
  Platform,
  SafeAreaView,
  StatusBar,
  StyleSheet,
  TextInput,
  View,
  Text,
  Button,
} from "react-native";
import { useEffect, useRef, useState } from "react";

export default function Home() {
  const qrLock = useRef(false);
  const appState = useRef(AppState.currentState);
  const [scannedData, setScannedData] = useState(null); // State to hold scanned QR data
  const [liters, setLiters] = useState(""); // State to hold liters input
  const [fuelStationId, setFuelStationId] = useState(""); // State to hold fuel station ID

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

    return () => {
      subscription.remove();
    };
  }, []);

  const handleBarcodeScanned = ({ data }) => {
    if (data && !qrLock.current) {
      qrLock.current = true;
      setScannedData(data); // Store the scanned data
    }
  };

  const handleConfirm = async () => {

    if (!scannedData || !liters || !fuelStationId) {
      alert("Please make sure all fields are filled.");
      return;
    }
    console.log("Scanned QR Data:", scannedData);
    console.log("Liters pumped:", liters);
    console.log("Fuel Station ID:", fuelStationId);


    try {
      const response = await fetch("http://your-backend-url/api/transactions/updateFuelQuota", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          stationId: fuelStationId,
          amount: liters,
          vehicleId: scannedData, // Assuming scannedData contains the vehicleId
        }),
      });

       const data = await response.json();

    if (response.ok) {
      alert("Fuel updated successfully!");
      setLiters("");
      setFuelStationId("");
      setScannedData(null);
      qrLock.current = false;
    } else {
      alert(`Error: ${data.message}`);
    }
  } catch (error) {
    console.error("Error while updating fuel quota:", error);
    alert("Something went wrong. Please try again.");
  }
};
  

  return (
    <SafeAreaView style={StyleSheet.absoluteFillObject}>
      <Stack.Screen
        options={{
          title: "Overview",
          headerShown: false,
        }}
      />
      {Platform.OS === "android" ? <StatusBar hidden /> : null}
      <CameraView
        style={StyleSheet.absoluteFillObject}
        facing="back"
        onBarcodeScanned={handleBarcodeScanned}
      />

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
          <Text style={styles.label}>Enter Fuel Station ID</Text>
          <TextInput
            style={styles.input}
            placeholder="Enter fuel station ID"
            value={fuelStationId}
            onChangeText={setFuelStationId}
          />
          <Button title="Confirm" onPress={handleConfirm} />
        </View>
      )}
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  overlay: {
    position: "absolute",
    bottom: 50,
    left: 20,
    right: 20,
    backgroundColor: "white",
    padding: 20,
    borderRadius: 10,
    shadowColor: "#000",
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.25,
    shadowRadius: 4,
    elevation: 5,
  },
  label: {
    fontSize: 16,
    marginBottom: 10,
    fontWeight: "bold",
  },
  input: {
    height: 40,
    borderColor: "gray",
    borderWidth: 1,
    borderRadius: 5,
    marginBottom: 10,
    paddingHorizontal: 10,
  },
});
