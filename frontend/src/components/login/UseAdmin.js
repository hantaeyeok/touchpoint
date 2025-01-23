import { useEffect, useState } from "react";
import Cookies from "js-cookie";
import {jwtDecode} from "jwt-decode";

const UseAdmin = () => {
  const [userInfo, setUserInfo] = useState({ userId: null, role: null });

  useEffect(() => {
    const token = Cookies.get("authToken"); // 쿠키에서 토큰 가져오기

    if (token) {
      try {
        const decodedToken = jwtDecode(token); // 토큰 디코딩
        console.log(decodedToken);
        const userId = decodedToken.sub; // JWT의 sub 필드에서 userId 추출
        const role = decodedToken.role || "USER"; // JWT의 role 필드에서 role 추출 (기본값 "USER")
        setUserInfo({ userId, role });
        console.log(`User ID: ${userId}, Role: ${role}`);
      } catch (error) {
        console.error("Error decoding token:", error);
      }
    } else {
      console.warn("No token found in cookies");
    }
  }, []);

  return userInfo;
};

export default UseAdmin;