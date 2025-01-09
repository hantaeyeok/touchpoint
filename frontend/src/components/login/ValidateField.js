export const validateField = (name, value, formData = {}) => {
    let isValid = true;
    let message = "";
  
    switch (name) {
      case "username":
        if (!value) {
          isValid = false;
          message = "아이디를 입력해주세요.";
        } else if (!value.match(/^[a-zA-Z0-9]{5,20}$/)) {
          isValid = false;
          message = "아이디는 5~20자의 영문/숫자 조합이어야 합니다.";
        }
        break;
  
      case "password":
        if (!value) {
          isValid = false;
          message = "비밀번호를 입력해주세요.";
        } else if (!value.match(/^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/)) {
          isValid = false;
          message = "비밀번호는 영문, 숫자, 특수문자를 포함하여 8자 이상이어야 합니다.";
        }
        break;
  
      case "confirmPassword":
        if (!value) {
          isValid = false;
          message = "비밀번호 확인을 입력해주세요.";
        } else if (value !== formData.password) {
          isValid = false;
          message = "비밀번호가 일치하지 않습니다.";
        }
        break;
  
      case "email":
        if (!value) {
          isValid = false;
          message = "이메일을 입력해주세요.";
        } else if (!value.match(/^[^\s@]+@[^\s@]+\.[^\s@]+$/)) {
          isValid = false;
          message = "올바른 이메일 형식이 아닙니다.";
        }
        break;
  
      case "name":
        if (!value) {
          isValid = false;
          message = "이름을 입력해주세요.";
        } else if (!value.match(/^[가-힣]{2,10}$/)) {
          isValid = false;
          message = "이름은 2~10자의 한글이어야 합니다.";
        }
        break;
  
      case "phone":
        if (!value) {
          isValid = false;
          message = "휴대전화번호를 입력해주세요.";
        } else if (!value.match(/^01[016789]-?\d{3,4}-?\d{4}$/)) {
          isValid = false;
          message = "올바른 휴대전화번호 형식이 아닙니다.";
        }
        break;
  
      default:
        break;
    }
  
    return { isValid, message };
  };