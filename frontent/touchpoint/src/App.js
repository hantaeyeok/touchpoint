import logo from './logo.svg';
import './App.css';
import ButtonLogin from './components/ButtonLogin';
import Menubar from './components/Menubar';
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";


function App() {
  return (
    <>
      <Menubar></Menubar>
      <Router>
        <div>
          <ButtonLogin url="/signup" className='buttonlogin'>회원가입</ButtonLogin>
        </div>
      </Router>
    </>
  );
}

export default App;
