import { Routes, Route, Navigate } from "react-router-dom";
import RecommendationView from "./modules/recommendation/views/RecommendationView";
import CaloriesView from "./modules/calories/views/CaloriesView";
import DietPage from "./modules/Macronutritions/views/DietPage";
import IngredientsPage from "./modules/ingredients/views/IngredientsPage";

export const routes = [
  { path: "/calories", label: "Calories brûlées", element: <CaloriesView /> },
  { path: "/recommendation", label: "Recommendation", element: <RecommendationView /> },
  { path: "/diet", label: "Diet Goal Road", element: <DietPage /> },
];

export default function AppRouter() {
  return (
    <Routes>
      <Route path="/" element={<Navigate to="/recommendation" replace />} />
      {routes.map((r) => (
        <Route key={r.path} path={r.path} element={r.element} />
      ))}
      <Route path="/ingredients" element={<IngredientsPage />} />
      <Route path="*" element={<Navigate to="/recommendation" replace />} />
    </Routes>
  );
}
