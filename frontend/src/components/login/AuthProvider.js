import React, { createContext, useContext, useState, useEffect } from "react";
import axios from "axios";
import Cookies from "js-cookie";
import {jwtDecode} from "jwt-decode"; // `jwt-decode` 라이브러리

// Context 생성
const AuthContext = createContext();

// AuthProvider 컴포넌트
export const AuthProvider = ({ children }) => {
  const [authState, setAuthState] = useState({
    token: null,
    userId: null,
    role: null,
    isAuthenticated: false,
    loading: true, // 초기 로딩 상태
    error: null,
  });

  // 쿠키에서 토큰 읽고 상태 업데이트
  const loadAuthStateFromCookie = () => {
    const token = Cookies.get("authToken");
    if (token) {
      try {
        const decoded = jwtDecode(token); // 토큰 디코딩
        setAuthState((prev) => ({
          ...prev,
          token,
          userId: decoded.sub,
          role: decoded.role || "ROLE_USER",
          isAuthenticated: true,
          loading: false,
          error: null,
        }));
      } catch (error) {
        console.error("Invalid token:", error);
        setAuthState((prev) => ({
          ...prev,
          token: null,
          userId: null,
          role: null,
          isAuthenticated: false,
          loading: false,
          error: "Invalid token",
        }));
      }
    } else {
      setAuthState((prev) => ({
        ...prev,
        token: null,
        userId: null,
        role: null,
        isAuthenticated: false,
        loading: false,
      }));
    }
  };

  // 서버에서 /check-cookie 호출
  const fetchAuthStateFromServer = async () => {
    try {
      const response = await axios.get("http://localhost:8989/login/check-cookie", {
        withCredentials: true,
      });
      console.log("API 응답 데이터:", response.data);
      setAuthState((prev) => ({
        ...prev,
        userId: response.data.userInfo.userId,
        role: response.data.userInfo.role,
        isAuthenticated: true,
        loading: false,
        error: null,
      }));
    } catch (error) {
      console.error("Failed to fetch auth state:", error);
      setAuthState((prev) => ({
        ...prev,
        token: null,
        userId: null,
        role: null,
        isAuthenticated: false,
        loading: false,
        error: error.response?.data?.message || "Failed to authenticate.",
      }));
    }
  };

  // 로그아웃
  const logout = () => {
    Cookies.remove("authToken", { path: "/" }); // 쿠키 삭제
    setAuthState({
      token: null,
      userId: null,
      role: null,
      isAuthenticated: false,
      loading: false,
      error: null,
    });
  };

  // 인증 상태 강제 갱신 함수
  const refreshAuthState = () => {
    loadAuthStateFromCookie();
    fetchAuthStateFromServer();
  };

  // 초기 실행
  useEffect(() => {
    loadAuthStateFromCookie();
    fetchAuthStateFromServer();
  }, []);

  return (
    <AuthContext.Provider value={{ ...authState, logout, refreshAuthState }}>
      {children}
    </AuthContext.Provider>
  );
};

// Context 사용을 위한 커스텀 훅
export const useAuth = () => useContext(AuthContext);
