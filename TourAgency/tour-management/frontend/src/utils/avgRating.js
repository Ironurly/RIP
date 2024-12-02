const calculateAvgRating = (reviews = []) => {
  if (reviews.length === 0) {
    return { totalRating: 0, avgRating: 0 }; // Если нет отзывов, возвращаем 0
  }

  const totalRating = reviews.reduce((acc, item) => acc + item.rating, 0);
  const avgRating = (totalRating / reviews.length).toFixed(1);

  return {
    totalRating,
    avgRating: parseFloat(avgRating), // Преобразуем строку в число
  };
};

export default calculateAvgRating;
