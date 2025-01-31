import React, { useEffect, useState } from 'react';
import { Link } from "react-router-dom";
import "@styles/Pagenation.css";

const Pagenation = ({ totalItems, itemCountPerPage, pageCount, currentPage, onPageChange }) => {
    
    const totalPages = Math.ceil(totalItems / itemCountPerPage);
    const [start, setStart] = useState(1);
    const noPrev = start === 1;
    const noNext = start + pageCount - 1 >= totalPages;
  
    useEffect(() => {
      if (currentPage === start + pageCount) setStart((prev) => prev + pageCount);
      if (currentPage < start) setStart((prev) => prev - pageCount);
    }, [currentPage, pageCount, start]);

    return (
      <div className="product_wrapper"> 
      <ul className ="product_ul">
        <li id='liCss' className={`products ${noPrev && 'invisible'}`} onClick={() => onPageChange(start - 1)}>
          <Link className="link" to={`?page=${start - 1}`}>이전</Link>
        </li>
        {[...Array(pageCount)].map((_, i) => (
          start + i <= totalPages && (
            <li id='liCss2' key={i}>
              <Link 
                className={`product_page ${currentPage === start + i && 'activeBtn'}`}
                to={`?page=${start + i}`}
                onClick={() => onPageChange(start + i)}
              >
                {start + i}
              </Link>
            </li>
          )
        ))}
        <li id='liCss3' className={`move ${noNext && 'invisible'}`} onClick={() => onPageChange(start + pageCount)}>
          <Link className="link" to={`?page=${start + pageCount}`}>다음</Link>
        </li>
      </ul>
    </div>
  );
}
export default Pagenation;
