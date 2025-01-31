import "@styles/Product.css";
import InfoImg from "@components/productcomponents/InfoImg";
import Search from "@components/productcomponents/Search";
import Pagenation from "@components/productcomponents/Pagenation"
import React, { useState, useEffect } from 'react';
import { Link, useNaviState } from "react-router-dom";
import axios from "axios";

const Product = () => {

  const [responseData, setResponseData] = useState([]); // 데이터 상태 저장
  const [clickCategory, setParam] = useState('키오스크/포스'); //카테고리 버튼 상태 저장 

   // 데이터를 가져오는 함수
    const fetchData = () => {
        axios({
        method: 'get',
        url: 'http://localhost:8989/product',
        params: { category: clickCategory }, // 쿼리 파라미터로 카테고리 전달
        responseType: 'json',
        })
        .then((response) => {
            const data = response.data.data;
            setResponseData(data); // 상태에 responseData 저장
        })
        .catch((error) => {
            console.error('Error fetching data:', error);
        });
    };

    

     // clickCategory 변경될 때마다 axios실행후 데이터를 가져옴
    useEffect(() => {
        fetchData();
        //여기에 버튼 색 고정 시켜놓으면 될듯
    }, [clickCategory]); // clickCategory 상태가 변경될 때 실행

    const onClick_kioskButton = (category) => {
        setParam('키오스크/포스');
        setActiveButton(category);
    };
    const onClick_cctvButton = (category) => {
        setParam('출입인증기/CCTV/인터넷');
        setActiveButton(category);
    };
    const onClick_otherButton = (category) => {
        setParam('부가상품');
        setActiveButton(category);
    };

   

    const ResponseProduct = (props) => {
        const data = props.data; // props 구조 분해
        return (
          <div className="product-component">
            <Link to={`/detailProduct/${data.productId}`} style={{ textDecoration: "none" }}>
            {/*<img className="proImg" src={process.env.PUBLIC_URL + data.thumbnailImage} alt={data.productName} />  */}{/*이미지를 동적으로 불러오고싶으면 publid폴더에 넣어야함*/}
            <img className="proImg" src={`http://localhost:8989${data.thumbnailImage}`} alt={data.productName} />
            <h4>{data.productName}</h4>
            <p className="proP">{data.shortDescription}</p>
            </Link></div>
        );
      };
      const [activeButton, setActiveButton] = useState(""); 

      const onClickButton = (category) => {
        setActiveButton(category);
        
      };

      const [currentPage, setCurrentPage] = useState(1); // 현재 페이지 상태

      // 페이지 변경 핸들러
      const handlePageChange = (newPage) => {
        setCurrentPage(newPage); // 페이지 변경 시 currentPage 업데이트
      };

      

  return (
    <>
      {/*product페이지 소개이미지*/}
      <InfoImg/>

      {/*검색창*/}
      <Search/>

      {/*카테고리 나뉘는 부분*/}
      <div className="style-divPro">
        <button className={`style-button1 ${ activeButton === "키오스크/포스" ? "active" : "" }`} onClick={() => onClick_kioskButton("키오스크/포스")}>키오스크 / 포스</button>      
        <button className={`style-button1 ${ activeButton === "출입인증기 / CCTV / 인터넷" ? "active" : "" }`} onClick={() => onClick_cctvButton("출입인증기 / CCTV / 인터넷")}>출입인증기 / CCTV / 인터넷</button>      
        <button className={`style-button1 ${ activeButton === "부가상품" ? "active" : "" }`} onClick={() => onClick_otherButton("부가상품")}>부가상품</button>      

      </div>
            
      <div className="product-list">
      
        {
          clickCategory === '키오스크/포스' 
          ? responseData.map((cctv, index) => (
            <ResponseProduct data={cctv} key={index} />
            ))
          : clickCategory === '부가상품' 
          ? responseData.map((otherItem, index) => (
            <ResponseProduct data={otherItem} key={index} />
            ))
          : responseData.map((kiosk, index) => (
            <ResponseProduct data={kiosk} key={index} />
            ))
        }
        



        <div>
          <button className="insert-button">
            {/*<Link to="/productInsert" style={{ textDecoration: "none"}}>상품 추가</Link>*/}
            <Link to="/productRegister" style={{ textDecoration: "none"}}>상품 추가</Link>
          </button>
        </div>
        {
        <Pagenation 
          totalItems={responseData.length}          // 총 아이템 수
          itemCountPerPage={3}     // 페이지당 아이템 수
          pageCount={5}             // 한 번에 보여줄 페이지 수
          currentPage={1}           // 현재 페이지
          onPageChange={handlePageChange}   // 페이지 변경 핸들러
        />
        }
      </div>
      
    </>
  );
}

export default Product;