import axios from 'axios';

// Axios 기본 설정
const instance = axios.create({
  baseURL: 'http://localhost:8989', // API 서버 주소
  withCredentials: true, // 쿠키를 포함한 요청 허용
});

export default instance;
