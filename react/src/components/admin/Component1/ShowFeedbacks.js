import React from "react";

function ShowFeedbacks(props) {
  const { feed } = props;
  return (
    <div className="alert alert-dark">
      <h3>{feed.feedbackDesc}</h3>
    </div>
  );
}

export default ShowFeedbacks;
