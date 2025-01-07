import "@styles/Product.css";


function Product() {
  
  const Product_compoment = () =>{
    return(
      <div className="product_compoment">
        <img src="https://cafe24.poxo.com/ec01/smartkiosk/fYw07Q+e08011Z5Qzbz30yEnneSL/1RqPliGGCSPfDRfaYid8dj7JhzdNC7ZDrE6l3wwD7DGFJLVP7MDZruvyg==/_/web/product/big/202103/8be661d787c1cacf899537185246e0d3.png"></img>
        <h4>무인매장 유통형 키오스크</h4>
        <p>멀리서도 제어가 가능한 무인매장 키오스크,  고성능 CPU 탑재</p> 
      </div>
    )
  };

  return (
    <div className="product-list">
        <Product_compoment></Product_compoment>
    </div>
  );
}

export default Product