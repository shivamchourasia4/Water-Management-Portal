import axios from "axios";
import React, { useState } from "react";
import { useHistory } from "react-router";
import { link } from "../../../Proxy/proxy";
import SendMail from "./SendMail";
import ShowFeedbacks from "./ShowFeedbacks";

function WaterList(props) {
  const {
    city,
    location,
    userId,
    feedback,
    waterDesc,
    waterPressure,
    wid,
    duration,
  } = props.info;

  const [edit, setEdit] = useState(true);
  const [area, setArea] = useState(location);
  const [city_a, setCity] = useState(city);
  const [duration_a, setDuration] = useState(duration);
  const [pressure, setPressure] = useState(waterPressure);
  const [desc, setDesc] = useState(waterDesc);
  const [message, setMessage] = useState("");
  const [showFeedbacks, setShowFeedbacks] = useState(false);
  const [isDeleted, setIsDeleted] = useState(false);
  const [showEmailPanel, setShowEmailPanel] = useState(false);
  const [showMessage, setShowMessage] = useState(false);

  const history = useHistory();

  const handleSubmit = (e) => {
    e.preventDefault();
    setEdit(true);
    // console.log(water);
    const update = async () => {
      const url = `${link}admin/${wid}`;
      await axios
        .put(
          url,
          {
            waterPressure: pressure,
            waterDesc: desc,
            location: area,
            city: city_a,
            duration: duration_a,
          },
          {
            headers: {
              Authorization: `Bearer ${localStorage.getItem("token")}`,
            },
          }
        )
        .then((res) => {
          setShowMessage(true);
          setMessage(res.data.message);
        })
        .catch((err) => {
          localStorage.removeItem("token");
          alert("Session Timed Out! Login Again.");
          history.push("/login");
        });
    };
    update();
  };

  const deleteInfo = () => {
    const delInfo = async () => {
      const url = `${link}admin/${wid}`;

      await axios
        .delete(url, {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`,
          },
        })
        .then((res) => {
          setIsDeleted(true);
          setMessage(res.data.message);
        })
        .catch((err) => {
          localStorage.removeItem("token");
          alert("Session Timed Out! Login Again.");
          history.push("/login");
        });
    };
    delInfo();
  };

  return (
    <div>
      {showMessage && (
        <div className="alert alert-primary" role="alert">
          {message}
        </div>
      )}
      <div className="container mt-2">
        {!isDeleted ? (
          <div>
            <div className="alert alert-success" role="alert">
              <p className="mb-0">Added By</p>
              <h4 className="alert-heading">
                {userId.firstName} {userId.lastName}
              </h4>
              <p className="mb-0">Mobile Number</p>
              <h4>{userId.mobileNumber}</h4>
              <p className="mb-0">Email</p>
              <h4>{userId.email}</h4>
              <hr></hr>
              {wid}
            </div>
            <form onSubmit={handleSubmit}>
              <div>
                <h3>City</h3>
                <input
                  value={city_a}
                  disabled={edit}
                  onChange={(e) => setCity(e.target.value)}
                  maxLength="30"
                  type="text"
                  required
                  className="form-control"
                ></input>
              </div>
              <h3>
                Location
                <input
                  value={area}
                  disabled={edit}
                  onChange={(e) => setArea(e.target.value)}
                  maxLength="100"
                  type="text"
                  required
                  className="form-control"
                ></input>
              </h3>
              <h3>
                Pressure
                <input
                  value={pressure}
                  disabled={edit}
                  onChange={(e) => setPressure(e.target.value)}
                  min="0"
                  max="100"
                  type="number"
                  required
                  className="form-control"
                ></input>
              </h3>
              <h3>
                Description
                <input
                  value={desc}
                  disabled={edit}
                  onChange={(e) => setDesc(e.target.value)}
                  maxLength="200"
                  required
                  className="form-control"
                ></input>
              </h3>
              <h3>
                Duration
                <input
                  value={duration_a}
                  disabled={edit}
                  onChange={(e) => setDuration(e.target.value)}
                  type="number"
                  min="0"
                  max="300"
                  required
                  className="form-control"
                ></input>
              </h3>
              <div className="row">
                <button type="submit" className="btn btn-primary my-3">
                  Update
                </button>
                {edit && (
                  <button
                    onClick={() => setEdit(false)}
                    className="btn btn-primary my-3"
                  >
                    Edit
                  </button>
                )}
              </div>
              <br />
            </form>
            <div className="row">
              <button
                onClick={() => setShowFeedbacks(!showFeedbacks)}
                className="btn btn-primary my-3"
              >
                View FeedBacks
              </button>
              {showFeedbacks && (
                <div>
                  {feedback.map((feed, index) => {
                    return (
                      <div key={index}>
                        <ShowFeedbacks feed={feed} />
                      </div>
                    );
                  })}
                </div>
              )}
              {showFeedbacks && feedback.length === 0 ? (
                <div className="alert alert-warning" role="alert">
                  <h2 className="alert-heading">No FeedBack!</h2>
                </div>
              ) : null}

              <button onClick={deleteInfo} className="btn btn-primary my-3">
                Delete Info
              </button>
            </div>
          </div>
        ) : null}

        <button
          onClick={() => setShowEmailPanel(!showEmailPanel)}
          className="btn btn-primary my-3"
        >
          Send Mail
        </button>

        {showEmailPanel && <SendMail to={userId.email} />}
        <hr />
      </div>
    </div>
  );
}

export default WaterList;
