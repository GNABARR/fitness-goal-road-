import { Routes, Route, Navigate } from "react-router-dom";
import RecommendationView from "./modules/recommendation/views/RecommendationView";
import CaloriesView from "./modules/calories/views/CaloriesView";

export const routes = [
  { path: "/calories", label: "Calories brûlées", element: <CaloriesView /> },
  { path: "/recommendation", label: "Recommendation", element: <RecommendationView /> },
];

export default function AppRouter() {
  return (
    <Routes>
      <Route path="/" element={<Navigate to="/recommendation" replace />} />
      {routes.map((r) => (
        <Route key={r.path} path={r.path} element={r.element} />
      ))}
      <Route path="*" element={<Navigate to="/recommendation" replace />} />
    </Routes>
  );
}
