import { View, Text, StyleSheet, SafeAreaView, Pressable, Alert } from "react-native";
import { Link, Stack } from "expo-router";
import { useCameraPermissions } from "expo-camera";

export default function Home() {
  const [permission, requestPermission] = useCameraPermissions();
  const isPermissionGranted = Boolean(permission?.granted);
  const isPermissionUndetermined = permission?.status === "undetermined";

  const handleRequestPermission = async () => {
    const response = await requestPermission();
    if (!response.granted) {
      Alert.alert(
        "Permission Denied",
        "Camera access is required to scan QR codes. Please enable it in your settings."
      );
    }
  };

  return (
    <SafeAreaView style={styles.container}>
      <Stack.Screen options={{ title: "Overview", headerShown: false }} />
      <Text style={styles.title}>QR Code Scanner</Text>

      

      <View style={styles.buttonContainer}>
        <Pressable onPress={handleRequestPermission}>
          <Text style={styles.buttonStyle}>
            {isPermissionGranted ? "Permission Granted" : "Request Permissions"}
          </Text>
        </Pressable>

        <Link href={"/scanner"}  asChild>
          
          <Pressable
            disabled={!isPermissionGranted}
            style={[
              styles.scanButton,
              { opacity: isPermissionGranted ? 1 : 0.5 },
            ]}
          >
            <Text style={styles.buttonStyle}>Scan Code</Text>
          </Pressable>
        </Link>
      </View>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  container: {
    gap:20,
    flex: 1,
    alignItems: "center",
    backgroundColor: "black",
    justifyContent: "center",
    padding: 20,
  },
  title: {
    color: "white",
    fontSize: 32,
    fontWeight: "bold",
    marginBottom: 40,
  },
  buttonContainer: {
    gap: 20,
    width: "100%",
    alignItems: "center",
  },
  buttonStyle: {
    color: "#0E7AFE",
    fontSize: 18,
    textAlign: "center",
  },
  scanButton: {
    borderColor: "#0E7AFE",
    borderWidth: 1,
    paddingVertical: 10,
    paddingHorizontal: 20,
    borderRadius: 10,
  },
});
