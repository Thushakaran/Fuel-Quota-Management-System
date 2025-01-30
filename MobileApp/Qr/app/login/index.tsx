import { View, Text, TextInput, Pressable, StyleSheet, Alert } from "react-native";
import { useRouter } from "expo-router";
import AsyncStorage from "@react-native-async-storage/async-storage";
import { useState } from "react";
import axios from "axios";

export default function Login() {
  const router = useRouter();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const handleLogin = async () => {
    console.log("gmrkgjk")
    try {
      console.log("gm")
      const response = await axios.post(
        "http://172.20.97.13:8080/api/auth/login",
        {
          userName: email,
          password : password,
          
          
          });
          console.log(response)
      

      const data = await response.data;
      console.log(response)
      if (response.status === 200) {
        await AsyncStorage.setItem("userToken", "dummy-token");
          router.replace("/(tabs)/scanner");
    } 

    }catch (error) {
      console.log("Error while updating fuel quota:", error);
      Alert.alert("Login Failed", "Invalid email or password");
    }

  

/*

    if (email === "test@example.com" && password === "password") {
      await AsyncStorage.setItem("userToken", "dummy-token");
      router.replace("/(tabs)/scanner");
    } else {
      Alert.alert("Login Failed", "Invalid email or password");
    }*/
  };

  return (
    <View style={styles.container}>
      <Text style={styles.title}>Sign In</Text>
      <TextInput
        style={styles.input}
        placeholder="Email"
        placeholderTextColor="#ccc"
        value={email}
        onChangeText={setEmail}
        autoCapitalize="none"
      />
      <TextInput
        style={styles.input}
        placeholder="Password"
        placeholderTextColor="#ccc"
        value={password}
        onChangeText={setPassword}
        secureTextEntry
      />
      <Pressable style={styles.button} onPress={handleLogin}>
        <Text style={styles.buttonText}>Log In</Text>
      </Pressable>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: "black",
    padding: 20,
  },
  title: {
    color: "white",
    fontSize: 32,
    fontWeight: "bold",
    marginBottom: 40,
  },
  input: {
    width: "100%",
    backgroundColor: "#1e1e1e",
    color: "white",
    padding: 15,
    borderRadius: 10,
    marginBottom: 20,
  },
  button: {
    backgroundColor: "#0E7AFE",
    paddingVertical: 15,
    paddingHorizontal: 40,
    borderRadius: 10,
  },
  buttonText: {
    color: "white",
    fontSize: 18,
    fontWeight: "bold",
  },
});
