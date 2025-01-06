import React from "react";
import Header from "../../components/Header";
import Footer from "../../components/Footer";
import ButtonLogin from "../../components/ButtonLogin"; // ButtonLogin 컴포넌트 import

const SignUp = () => {
  const [formData, setFormData] = useState({
    username: "",
    password: "",
    confirmPassword: "",
    email: "",
    name: "",
    phone: "",
  });




  return (
    <>
      <div className="signup-container">

      {/* 아이디 입력 */}
      <InputDiv label="아이디" type="text" value={formData.username} onChange={handleChange} placeholder="아이디를 입력해주세요." iconName="Account Circle" />

      {/* 비밀번호 입력 */}
      <InputDiv label="비밀번호" type="password" value={formData.password} onChange={handleChange} placeholder="비밀번호를 입력해주세요." iconName="Lock" />

      {/* 비밀번호 확인 */}
      <InputDiv label="비밀번호 확인" type="password" value={formData.confirmPassword} onChange={handleChange} placeholder="비밀번호를 다시 입력해주세요." iconName="Lock" />

      {/* 이메일 입력 */}
      <InputDiv label="이메일" type="email" value={formData.email} onChange={handleChange} placeholder="이메일을 입력해주세요." iconName="Email" />
      
      {/* 이름 입력 */}
      <InputDiv label="이름" type="text" value={formData.name} onChange={handleChange} placeholder="이름을 입력해주세요." iconName="Account Circle" />

      {/* 전화번호 입력 */}
      <InputDiv label="전화번호" type="tel" value={formData.phone} onChange={handleChange} placeholder="전화번호를 입력해주세요." iconName="Phone" />   

      
      {/* 회원가입 버튼 */}
      <div style={{ textAlign: "center", marginTop: "50px" }}>
        <ButtonLogin type="submit">회원가입</ButtonLogin>
      </div>

    </div>
    </>
  );
};

export default SignUp;