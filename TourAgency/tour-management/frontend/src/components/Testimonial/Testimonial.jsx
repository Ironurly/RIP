import React from "react";
import Slider from "react-slick";
import ava01 from "../../assets/images/ava-1.jpg";
import ava02 from "../../assets/images/ava-2.jpg";
import ava03 from "../../assets/images/ava-3.jpg";

const Testimonial = () => {

  const settings={
    dots:true,
    Infinite:true,
    autoplay:true,
    speed:1000,
    swipeToSlide:true,
    autoplaySpeed:2000,
    slidesToShow:3,

    responsive:[
      {
        breakpoint: 992,
        settings: {
          slidesToShow: 2,
          slidesToScroll: 1,
          Infinite: true,
          dots: true,
        },
      },
      {
        breakpoint: 576,
        settings: {
          slidesToShow: 1,
          slidesToScroll: 1,
        },
      }
    ]
  }
  return (
    <Slider {...settings}>
      <div className="testimonial py-4 px-3">
        <p>
          Lorem ipsum dolor sit amet consectetur adipisicing elit. Doloremque
          dolores aut, voluptatibus dolorem sed expedita. Aliquid vero ipsam
          harum, earum recusandae nostrum ullam expedita doloremque mollitia
          fuga temporibus quam. Eaque.
        </p>
        <div className="d-flex align-items-center gap-4 mt-3">
          <img src={ava01} alt="" className="w-25 h-25 rounded-2" />
          <div>
            <h2 className="mb-0 mt-3">John Wick</h2>
            <p>Customer</p>
          </div>
        </div>
      </div>
      <div className="testimonial py-4 px-3">
        <p>
          Lorem ipsum dolor sit amet consectetur adipisicing elit. Doloremque
          dolores aut, voluptatibus dolorem sed expedita. Aliquid vero ipsam
          harum, earum recusandae nostrum ullam expedita doloremque mollitia
          fuga temporibus quam. Eaque.
        </p>
        <div className="d-flex align-items-center gap-4 mt-3">
          <img src={ava02} alt="" className="w-25 h-25 rounded-2" />
          <div>
            <h2 className="mb-0 mt-3">Elena Tochka</h2>
            <p>Customer</p>
          </div>
        </div>
      </div>
      <div className="testimonial py-4 px-3">
        <p>
          Lorem ipsum dolor sit amet consectetur adipisicing elit. Doloremque
          dolores aut, voluptatibus dolorem sed expedita. Aliquid vero ipsam
          harum, earum recusandae nostrum ullam expedita doloremque mollitia
          fuga temporibus quam. Eaque.
        </p>
        <div className="d-flex align-items-center gap-4 mt-3">
          <img src={ava03} alt="" className="w-25 h-25 rounded-2" />
          <div>
            <h2 className="mb-0 mt-3">Vovchik Pop</h2>
            <p>Customer</p>
          </div>
        </div>
      </div>

      <div className="testimonial py-4 px-3">
        <p>
          Lorem ipsum dolor sit amet consectetur adipisicing elit. Doloremque
          dolores aut, voluptatibus dolorem sed expedita. Aliquid vero ipsam
          harum, earum recusandae nostrum ullam expedita doloremque mollitia
          fuga temporibus quam. Eaque.
        </p>
        <div className="d-flex align-items-center gap-4 mt-3">
          <img src={ava01} alt="" className="w-25 h-25 rounded-2" />
          <div>
            <h2 className="mb-0 mt-3">John Wick</h2>
            <p>Customer</p>
          </div>
        </div>
      </div>
    </Slider>
  );
};

export default Testimonial;
