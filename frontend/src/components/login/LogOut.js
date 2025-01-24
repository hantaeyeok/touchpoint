import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "@components/login/AuthProvider";

const LogOut = () => {
  const navigate = useNavigate();
  const { logout } = useAuth();

  useEffect(() => {
    logout(); // 상태 업데이트 및 쿠키 제거
    navigate("/");
  }, [logout, navigate]);

  return null;
};

export default LogOut;
