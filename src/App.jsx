import React, { useState, useEffect, useRef } from "react";
import axios from "axios";
import "./App.css";

const App = () => {
  const [bodyPoints, setBodyPoints] = useState([]);
  const [selectedBodyPoint, setSelectedBodyPoint] = useState(null);
  const [newPoint, setNewPoint] = useState({
    name: "",
    x: 0,
    y: 0,
    z: 0,
    vitesseX: 0,
    vitesseY: 0,
    accelerationX: 0,
    accelerationY: 0,
    masse: 0,
  });

  const canvasRef = useRef(null);

  useEffect(() => {
    axios
      .get("https://nbody-back-79c68c764a72.herokuapp.com/body")
      .then((response) => {
        setBodyPoints(response.data);
        console.log("Points du corps récupérés:", response.data);
      })
      .catch((error) => {
        console.error("Erreur de récupération des points du corps:", error);
      });
  }, []);

  const simulateMovement = () => {
    axios
      .post("https://nbody-back-79c68c764a72.herokuapp.com/body/simulate")
      .then((response) => {
        setBodyPoints(response.data);
      })
      .catch((error) => {
        console.error("Erreur lors de la simulation:", error);
      });
  };

  const reset = () => {
    axios
      .post("https://nbody-back-79c68c764a72.herokuapp.com/body/reset")
      .then((response) => {
        setBodyPoints(response.data);
      })
      .catch((error) => {
        console.error("Erreur lors de la réinitialisation:", error);
      });
  };

  const deleteWithHighCoordinates = () => {
    axios
      .delete(
        "https://nbody-back-79c68c764a72.herokuapp.com/body/deleteHighCoordinates"
      )
      .then((response) => {
        setBodyPoints(response.data);
      })
      .catch((error) => {
        console.error("Erreur lors de la suppression:", error);
      });
  };

  useEffect(() => {
    const intervalId = setInterval(() => {
      simulateMovement();
      deleteWithHighCoordinates();
    }, 100);
    return () => clearInterval(intervalId);
  }, []);

  const addNewPoint = () => {
    axios
      .post("https://nbody-back-79c68c764a72.herokuapp.com/body", newPoint)
      .then((response) => {
        setBodyPoints([...bodyPoints, response.data]);
        setNewPoint({
          name: "",
          x: 0,
          y: 0,
          z: 0,
          vitesseX: 0,
          vitesseY: 0,
          accelerationX: 0,
          accelerationY: 0,
          masse: 0,
        });
      })
      .catch((error) => {
        console.error("Erreur lors de l'ajout du point:", error);
      });
  };

  // Fonction pour dessiner sur le canvas
  const draw = () => {
    const canvas = canvasRef.current;
    const ctx = canvas.getContext("2d");
    ctx.clearRect(0, 0, canvas.width, canvas.height);

    bodyPoints.forEach((point) => {
      const radius = point.masse / 10; // Taille du point proportionnelle à la masse
      const color = selectedBodyPoint?.id === point.id ? "red" : "blue";

      ctx.beginPath();
      ctx.arc(point.x, point.y, radius, 0, 2 * Math.PI);
      ctx.fillStyle = color;
      ctx.fill();
      ctx.stroke();

      // Afficher les informations du point si sélectionné
      if (selectedBodyPoint?.id === point.id) {
        ctx.fillStyle = "black";
        ctx.font = "12px Arial";
        ctx.fillText(point.name, point.x + 10, point.y);
        ctx.fillText(`X: ${point.x}`, point.x + 10, point.y + 15);
        ctx.fillText(`Y: ${point.y}`, point.x + 10, point.y + 30);
      }
    });
  };

  // Mise à jour du canvas à chaque changement de points
  useEffect(() => {
    draw();
  }, [bodyPoints, selectedBodyPoint]);

  return (
    <div className="App">
      <h1>N-Body Simulation</h1>

      <div>
        <h3>Créer un nouveau point</h3>
        <button type="button" onClick={addNewPoint}>
          Ajouter un point
        </button>
        <button type="button" onClick={reset}>
          Reset
        </button>
      </div>

      <div className="canvas-container">
        <canvas
          ref={canvasRef}
          width="800"
          height="600"
          onClick={(e) => {
            const rect = canvasRef.current.getBoundingClientRect();
            const x = e.clientX - rect.left;
            const y = e.clientY - rect.top;

            const point = bodyPoints.find(
              (p) => Math.abs(p.x - x) < 10 && Math.abs(p.y - y) < 10
            );
            if (point) {
              setSelectedBodyPoint(point);
            }
          }}
        />
      </div>
    </div>
  );
};

export default App;
