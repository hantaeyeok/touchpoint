import React from "react";
import { BrowserRouter as Router, Routes, Route, useNavigate } from 'react-router-dom'; // 올바른 import
import Login from '@pages/login/Login'; // Login 컴포넌트 import
import SignUp from '@pages/login/SignUp'; // SignUp 컴포넌트 import
import Menubar from '@components/indexcomponts/Menubar';
import './App.css'

function App() {
  return (
    <Router>
      <Menubar/>
      <Routes>
        <Route path="/login" element={<Login />} /> {/* /login 경로 */}
        <Route path="/signup" element={<SignUp />} /> {/* /signup 경로 */}
      </Routes>
    </Router>
  );
}

export default App;