import "@styles/Product.css";
import Categoty from "@components/productcomponents/Categoty"
import InfoImg from "@components/productcomponents/InfoImg"
import Search from "@components/productcomponents/Search";

function Product() {

  // 예시 데이터(db에서 조회)
  /*
  const productObj1 = {
    src: "https://cafe24.poxo.com/ec01/smartkiosk/fYw07Q+e08011Z5Qzbz30yEnneSL/1RqPliGGCSPfDRfaYid8dj7JhzdNC7ZDrE6l3wwD7DGFJLVP7MDZruvyg==/_/web/product/big/202011/0b4ff0828e5987a606060eae6130907c.jpg",
    title: "제품 제목11",
    intro: "멀리서도 제어가 가능한 무인매장 키오스크, 고성능 CPU 탑재1"
  };
  const productObj2 = {
    src: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQRgMWi3-7Qq89-b5mxwOsNKAIVV27NC_9ITw&s",
    title: "제품 제목22",
    intro: "멀리서도 제어가 가능한 무인매장 키오스크, 고성능 CPU 탑재2",
  };
  const productObj3 = {
    src: "https://cafe24.poxo.com/ec01/smartkiosk/fYw07Q+e08011Z5Qzbz30yEnneSL/1RqPliGGCSPfDRfaYid8dj7JhzdNC7ZDrE6l3wwD7DGFJLVP7MDZruvyg==/_/web/product/big/202103/8be661d787c1cacf899537185246e0d3.png",
    title: "제품 제목33",
    intro: "멀리서도 제어가 가능한 무인매장 키오스크, 고성능 CPU 탑재3",
  };
  
  const responseData = [productObj1, productObj2, productObj3];
  */

  

  return (
    <>
      {/*product페이지 소개이미지*/}
      <InfoImg/>

      {/*검색창*/}
      <Search/>

      {/*카테고리 나뉘는 부분*/}
      <Categoty/>
      

    </>
    
  );
}

export default Product;
