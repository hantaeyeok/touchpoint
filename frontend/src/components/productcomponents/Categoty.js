import "@styles/Product.css";
import '@styles/Categoty.css';
import kioskData from '@testJson/kioskData.json'
import cctvData from '@testJson/cctvData.json'
import other_itemData from '@testJson/other_itemData.json'
import { useState } from "react";


const Categoty = () => {
    
    const ResponseProduct = (props) => {
        const data = props.data; // props 구조 분해
        return (
          <div className="product-component">
            <img src={data.src} />
            <h4>{data.title}</h4>
            <p>{data.intro}</p>
          </div>
        );
    };

    //console.log(kioskData); //키오스크 json데이터 출력됨
    //console.log(cctvData);


    const [type, setType] = useState('kioskData');

    const onClick_kioskButton = () => {
        setType('kiosk');
    };

    const onClick_cctvButton = () => {
        setType('cctv');
    };

    const onClick_otherButton = () => {
        setType('otherItem');
    };

    return(
        <>
            

            

            <div className="style-div">
                <button className="style-button" onClick={onClick_kioskButton}>키오스크 / 포스</button>
                <button className="style-button" onClick={onClick_cctvButton}>출입인증기 / CCTV / 인터넷</button>
                <button className="style-button" onClick={onClick_otherButton}>부가상품</button>
             
            </div>
            
            <div className="product-list">
            {
                type === 'cctv' 
                ? cctvData.map((cctv, index) => (
                    <ResponseProduct data={cctv} key={index} />
                    ))
                : type === 'otherItem' 
                ? other_itemData.map((otherItem, index) => (
                    <ResponseProduct data={otherItem} key={index} />
                    ))
                : kioskData.map((kiosk, index) => (
                    <ResponseProduct data={kiosk} key={index} />
                    ))
            }
            </div>
        
        </>
    )

}

export default Categoty;