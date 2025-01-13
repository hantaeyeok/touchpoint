import './App.css';
import Product from '@src/page/Product';
import ProductInsert from '@src/page/ProductInsert';
import Menubar from '@components/indexcomponents/Menubar';
import { Route, Routes, useNavigate } from 'react-router-dom';

function App() {

  return (
    <div className="App">
      <Menubar/>

      <Routes>
        <Route path="/" element={<h1>메인</h1>} />
        <Route path="/product" element={<Product/>}/>
        <Route path="/productInsert" element={<ProductInsert/>}/>
      </Routes>

    </div>

    
  );
}

export default App;
