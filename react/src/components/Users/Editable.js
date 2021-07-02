import axios from "axios";
import React, { useState } from "react";
import { link } from "../../Proxy/proxy";
import AddFeedback from "./AddFeedback";
import { useHistory } from "react-router";

function Editable(props) {
  const { water, user } = props;

  const [edit, setEdit] = useState(true);
  const [area, setArea] = useState(water.location);
  const [city, setCity] = useState(water.city);
  const [duration, setDuration] = useState(water.duration);
  const [pressure, setPressure] = useState(water.waterPressure);
  const [desc, setDesc] = useState(water.waterDesc);
  const [message, setMessage] = useState("");
  const [feedback, setFeedback] = useState(false);
  const [showMessage, setShowMessage] = useState(false);

  const history = useHistory();

  const handleSubmit = (e) => {
    e.preventDefault();
    setEdit(true);
    // console.log(water);
    const update = async () => {
      const url = `${link}admin/${water.wid}`;

      await axios
        .put(
          url,
          {
            waterPressure: pressure,
            waterDesc: desc,
            location: area,
            city: city,
            duration: duration,
          },
          {
            headers: {
              Authorization: `Bearer ${localStorage.getItem("token")}`,
            },
          }
        )
        .then((res) => {
          setMessage(res.data.message);
          setShowMessage(true);
        })
        .catch((err) => {
          localStorage.removeItem("token");
          alert("Session Timed Out! Login Again.");
          history.push("/login");
        });
    };
    update();
  };

  return (
    <div>
      {showMessage && (
        <div className="alert alert-primary" role="alert">
          {message}
        </div>
      )}
      <form onSubmit={handleSubmit} className="row">
        <div className="col">
          <h3>City</h3>
          <input
            value={city}
            disabled={edit}
            onChange={(e) => setCity(e.target.value)}
            maxLength="30"
            type="text"
            className="form-control"
            required
          ></input>
        </div>
        <div className="col">
          <h3>Location</h3>
          <input
            value={area}
            disabled={edit}
            onChange={(e) => setArea(e.target.value)}
            maxLength="100"
            type="text"
            required
            className="form-control"
          ></input>
        </div>
        <div>
          <h3>Pressure</h3>
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
        </div>
        <div>
          <h3>Description</h3>
          <input
            value={desc}
            disabled={edit}
            onChange={(e) => setDesc(e.target.value)}
            maxLength="200"
            required
            className="form-control"
          ></input>
        </div>
        <div>
          <h3>Duration</h3>
          <input
            value={duration}
            disabled={edit}
            onChange={(e) => setDuration(e.target.value)}
            type="number"
            min="0"
            max="300"
            required
            className="form-control"
          ></input>
        </div>

        <button
          type="submit"
          disabled={user !== water.userId.email}
          className="btn btn-primary my-3"
        >
          Update
        </button>
        {user === water.userId.email ? (
          <>
            {edit && (
              <button
                className="btn btn-primary my-3"
                onClick={() => setEdit(false)}
              >
                Edit
              </button>
            )}
          </>
        ) : (
          <button
            disabled={true}
            title="You cannot edit other's data"
            type="button"
            className="btn btn-primary my-3"
          >
            Edit
          </button>
        )}
        {duration < 30 ? (
          <button
            type="button"
            className="btn btn-primary my-3"
            onClick={() => setFeedback(!feedback)}
          >
            Add Feedback
          </button>
        ) : (
          <button
            disabled={true}
            title="Feedback allowed only when duration is less than 30 mins."
            type="button"
            className="btn btn-primary my-3"
          >
            Add Feedback
          </button>
        )}
        <br />
      </form>

      {feedback ? <AddFeedback waterId={water.wid} /> : null}

      <hr />
    </div>
  );
}

export default Editable;
