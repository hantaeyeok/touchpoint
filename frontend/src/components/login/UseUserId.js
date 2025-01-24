import { useEffect, useState } from "react";
import Cookies from "js-cookie";
import {jwtDecode} from "jwt-decode";


const useUserId = () => {
  const [userId, setUserId] = useState(null);

  useEffect(() => {
    const token = Cookies.get("authToken"); // Get the token from cookies

    if (token) {
      try {
        const decodedToken = jwtDecode(token); // Decode the token
        setUserId(decodedToken.sub); // Extract the userId (sub field)
        console.log(userId);
      } catch (error) {
        console.error("Error decoding token:", error);
      }
    } else {
      console.warn("No token found in cookies");
    }
  }, []);

  return userId;
};

export default useUserId;
