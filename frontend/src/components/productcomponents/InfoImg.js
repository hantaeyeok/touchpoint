import "@styles/InfoImg.css";


const imageUrl = `${process.env.PUBLIC_URL}/images/productIntro.png`;
function InfoImg() {

    
    
    return (
        
        <div className="infoImg">
            <img src={imageUrl} alt="Banner Image" className="banner-image" />
            
            <div className="text-overlay">
                <p className="service-name">TouchPoint Service</p>
                <h1 className="main-text">매장에 필요한 모든것, TouchPoint</h1>
                <div className="page-indicator">
                    <div className="page-num" >
                        <span className="current-page">1</span> / <span className="total-page">5</span>
                    </div>
                </div>
            </div>
        </div>
    )

};

export default InfoImg;
