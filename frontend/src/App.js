import './App.css';
import Product from '@src/page/Product';
import Menubar from '@components/indexcomponents/Menubar';
import { Route, Routes, useNavigate } from 'react-router-dom';

function App() {

  



  return (
    <div className="App">
      <Menubar/>

      <Routes>
        <Route path="/" element={<h1>메인</h1>} />
        <Route path="/product" element={<Product/>}/>
        <Route path="/*" element={<h1>없는 페이지입니다.</h1>} />
        
      </Routes>

    </div>

    
  );
}

export default App;
