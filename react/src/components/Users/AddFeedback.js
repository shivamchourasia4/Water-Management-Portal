import axios from "axios";
import React, { useState } from "react";
import { useHistory } from "react-router";
import { link } from "../../Proxy/proxy";

function AddFeedback(props) {
  const { waterId } = props;

  const [desc, setDesc] = useState("");
  const [message, setMessage] = useState("");
  const [showMessage, setShowMessage] = useState(false);

  const history = useHistory();

  const handleSubmit = (e) => {
    e.preventDefault();
    // console.log(desc);

    const addFeedback = async () => {
      const url = `${link}addFeedback`;

      await axios
        .post(
          url,
          {
            waterId: waterId,
            feedbackDesc: desc,
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
    addFeedback();
    document.getElementById("feedbackform").reset();
  };

  return (
    <div>
      {showMessage && (
        <div className="alert alert-primary" role="alert">
          {message}
        </div>
      )}
      <form onSubmit={handleSubmit} id="feedbackform" className="row">
        <label className="form-label">Feedback</label>
        <textarea
          maxLength="100"
          required
          className="form-control"
          onChange={(e) => setDesc(e.target.value)}
        ></textarea>
        <button type="submit" className="btn btn-primary my-3">
          Submit Feedback
        </button>
      </form>
    </div>
  );
}

export default AddFeedback;
