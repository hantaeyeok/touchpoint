import React from "react";
import Header from "../../components/Header";
import Footer from "../../components/Footer";
import ButtonLogin from "../../components/ButtonLogin"; // ButtonLogin 컴포넌트 import

const SignUp = () => {
  return (
    <div>
      {/* Header 표시 */}
      <Header />

      {/* 회원가입 버튼 */}
      <div style={{ textAlign: "center", marginTop: "50px" }}>
        <ButtonLogin type="submit">회원가입</ButtonLogin>
      </div>

      {/* Footer 표시 */}
      <Footer />
    </div>
  );
};

export default SignUp;