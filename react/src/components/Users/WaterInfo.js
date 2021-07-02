import axios from "axios";
import React, { useState } from "react";
import { link } from "../../Proxy/proxy";
import jwt_decode from "jwt-decode";
import Editable from "./Editable";
import { useHistory } from "react-router";

export default function WaterInfo() {
  const [city, setCity] = useState("");
  const [results, setResults] = useState([]);
  const [isEmpty, setIsEmpty] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  const [user, setUser] = useState("");

  const history = useHistory();

  const searchCity = (e) => {
    const search = async () => {
      const url = `${link}searchWaterInfo`;

      await axios
        .post(
          url,
          {
            city: city,
          },
          {
            headers: {
              Authorization: `Bearer ${localStorage.getItem("token")}`,
            },
          }
        )
        .then((res) => {
          if (res.data.length === 0) {
            setIsEmpty(true);
            setIsLoading(false);
          } else {
            setResults(res.data);
            setIsEmpty(false);
            setIsLoading(false);
          }
          //   console.log(res.data);
          //   console.log(res.data.length);
          //   console.log();
        })
        .catch((err) => {
          // setIsLoading(false);
          localStorage.removeItem("token");
          alert("Session Timed Out! Login Again.");
          history.push("/login");
        });
    };
    e.preventDefault();
    setIsLoading(true);
    // console.log(jwt_decode(localStorage.getItem("token")));
    setUser(jwt_decode(localStorage.getItem("token")).email);
    search();
  };

  return (
    <div className="">
      <div className="container">
        <form onSubmit={searchCity}>
          <input
            type="search"
            required
            placeholder="Search City"
            maxLength="30"
            className="form-control"
            onChange={(e) => setCity(e.target.value)}
          ></input>
          <button type="submit" className="btn btn-primary my-3">
            Search
          </button>
        </form>

        {isEmpty ? (
          <div>
            {isLoading ? (
              <h1>Loading...</h1>
            ) : (
              <div className="alert alert-warning" role="alert">
                <h2 className="alert-heading">No Search Result!</h2>
              </div>
            )}
          </div>
        ) : (
          <div>
            {isLoading ? (
              <h1>Loading...</h1>
            ) : (
              <div>
                {results.map((water, index) => {
                  return (
                    <div key={index}>
                      <Editable water={water} user={user} />
                    </div>
                  );
                })}
              </div>
            )}
          </div>
        )}
      </div>
    </div>
  );
}
