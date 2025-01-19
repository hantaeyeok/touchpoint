import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';
import ProductForm from './ProductForm';

const ProductEdit = () => {
  const { productId } = useParams();
  const [initialData, setInitialData] = useState(null);
  //console.log("initialData:",initialData);
  const [images, setImages] = useState([]);
  const [detailImage, setDetailImage] = useState([]);   //images가 자꾸 배열이 아닌 다른거로 넘어오는것 같아서 새로 만듦
  //console.log("images:",images);  //들어옴

  useEffect(() => {
    axios
      .get(`http://localhost:8989/product/${productId}`)
      .then((response) => {
        console.log("response.data.data.:",response.data.data);
        setInitialData(response.data.data.product);
        setImages(response.data.data.images);
        
        setDetailImage(response.data.data.images);
        
      })
      .catch((error) => {
        console.error('Error fetching product details:', error);
      });
  }, [productId]);

    console.log("images",images);
    console.log("detailImage",detailImage);

  //상품수정
  const handleEdit = (initialData, detailImage) => {
    console.log("Initial detailImage value:", detailImage);  //안나옴옴

    const formData = new FormData();
    formData.append('product', JSON.stringify(initialData));

    if (initialData.thumbnailImage) {
        formData.append('upfile', initialData.thumbnailImage);
    } else {
        console.error("Thumbnail image is missing!");
    }

    if (!Array.isArray(detailImage)) {
        console.error("Images is not an array. Converting to array...");
        detailImage = Array.from(detailImage); // 배열로 변환
        setDetailImage(detailImage);
    }

    detailImage.forEach((imageFile) => {
        if (imageFile.originFile) {
            formData.append('images', imageFile.originFile);
        }
    });


    console.log("detailImage :", detailImage); 

    // FormData 디버깅
    for (let [key, value] of formData.entries()) {
        console.log(`${key}:`, value);
    }

    axios
      .put(`http://localhost:8989/product/${productId}`, formData, {
          headers: { 'Content-Type': 'multipart/form-data' },
      })
      .then((response) => {
          alert('상품이 수정되었습니다!');
      })
      .catch((error) => {
          console.error('Error updating product:', error);
      });
};



  if (!initialData) return <div>Loading...</div>;

  return (
    <div>
      <h2>상품 수정</h2>
      <ProductForm images={images} initialData={initialData} onSubmit={handleEdit} method="GET"/>
    </div>
  );
};

export default ProductEdit;
