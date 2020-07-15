import React from "react";
import MovieModel from "../types/MovieModel";

const Movie = (props: MovieModel) => {
  return (
    <tr>
      <td>{props.name}</td>
      <td>{props.genre}</td>
    </tr>
  );
};

export default Movie;
