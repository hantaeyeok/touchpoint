import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';
import ProductForm from '@components/productcomponents/ProductForm';

const ProductEdit = () => {



  const convertUrlToFile = async (url, fileName) => {  //url, fileName을 받아 파일객체로 변환해주는 함수
    const response = await fetch(url);
    const blob = await response.blob();
    const file = new File([blob], fileName, { type: blob.type });
    return file;
  };

  const { productId } = useParams();
  const [initialData, setInitialData] = useState(null);
  const [images, setImages] = useState([]);

  const [deleteImg, setDeleteImg] = useState([]);

  //상품 수정시에 저장되있던 값 넣어줌
  useEffect(() => {
    axios
      .get(`http://localhost:8989/product/${productId}`)
      .then((response) => {
        console.log("response.data.data.:",response.data.data);
        setInitialData(response.data.data.product);  //백에서 product안에 상세이미지도 담겨서 넘어옴
        setImages(response.data.data.images);
      })
      .catch((error) => {
        console.error('Error fetching product details:', error);
      });
  }, [productId]);

    const handleEdit = async  (productData, mainImg, imgList,  deleteImg) => {
      const formData = new FormData();

      // 백에 넘길 상품 정보에서 이미지, 날짜 데이터 제거 (productInfo에서는 글 정보만 넘길 것)
      const productInfo = { ...productData };
      delete productInfo.thumbnailImage; 
      delete productInfo.productImages; 
      delete productInfo.createdDate; 

      formData.append('product', JSON.stringify(productInfo));
      console.log("productInfo",productInfo);

      // formData에 썸네일 파일객체 추가
      if (mainImg instanceof File) {
          formData.append('upfile', mainImg); // 파일 객체인 경우 FormData에 추가
          console.log("mainImg",mainImg);
      }


      // formData에 상세 이미지 파일객체 추가
      const promises = imgList.map((imageFile) => {   //imgList 배열의 각 항목에 map하기위해 비동기 작업(Promise)을 생성
        if (imageFile.originFile instanceof File) {   // 파일 객체인 경우
            return Promise.resolve({ file: imageFile.originFile, id: imageFile.id }); //파일객체(파일과 아이디)를 반환
        } else if (imageFile.previewUrl) {             // previewUrl만 있는 경우(이미 저장이 된 이미지)
            return convertUrlToFile(imageFile.previewUrl, `image-${imageFile.id}.jpg`)          //convertUrlToFile함수로 파일객체로 변환
                .then((file) => ({ file, id: imageFile.id }))                         //파일객체(파일과 아이디)를 반환환
                .catch((error) => {
                    console.error('Error converting URL to file:', error);
                    throw error;
                });
        }
      });


      

      setDeleteImg(setDeleteImg);
      console.log('전달받은 deleteImg : ', deleteImg);

      //전달받은 삭제이미지 배열 같이 append
      formData.append('deleteImg', JSON.stringify(deleteImg));
      console.log('전달보낸 deleteImg : ', deleteImg);


      
      // promises배열의 모든 Promise가 완료된 후 순서대로 처리
      Promise.all(promises)
        .then((results) => {
            results.forEach((result) => {
                formData.append('images', result.file); // 원래 순서대로 상세이미지를 append
                console.log("Appending image with ID:", result.id);
            });
        })
        .catch((error) => {
            console.error('Error processing images:', error);
        });
      console.log("imgList",imgList);

      console.log([...formData.entries()]);

      try {
          await Promise.all(promises);  // 모든 비동기 작업이 끝날 때까지 기다림

          // Axios 요청
          axios
            .put(`http://localhost:8989/product/${productId}`, formData)
            .then((response) => {
              alert('상품이 수정되었습니다!');
              console.log("response:",response);
            })
            .catch((error) => {
              console.error('Error updating product:', error);
            });
        } catch (error) {
          console.error('Error processing images:', error);
        }
    };

  if (!initialData) return <div>Loading...</div>;

  return (
    <div>
      <h2>상품 수정</h2>
      <ProductForm images={images} initialData={initialData} method="PUT" onSubmit={handleEdit}/>
    </div>
  );
};

export default ProductEdit;