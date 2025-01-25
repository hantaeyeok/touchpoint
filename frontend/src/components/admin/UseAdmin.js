import { useEffect, useState } from "react";
import Cookies from "js-cookie";
import {jwtDecode} from "jwt-decode";

const UseAdmin = () => {
  const [userInfo, setUserInfo] = useState({ userId: null, role: null });

  const updateUserInfo = () => {
    const token = Cookies.get("authToken");
    if (token) {
      try {
        const decodedToken = jwtDecode(token); // jwt-decode로 디코딩
        const userId = decodedToken.sub;
        const role = decodedToken.role || "USER"; // 기본값 설정
        setUserInfo({ userId, role });
      } catch (error) {
        console.error("Error decoding token:", error);
        setUserInfo({ userId: null, role: null });
      }
    } else {
      setUserInfo({ userId: null, role: null });
    }
  };

  useEffect(() => {
    updateUserInfo();
  }, []);

  return { ...userInfo, updateUserInfo };
};

export default UseAdmin;
