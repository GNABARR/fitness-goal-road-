import { BrowserRouter as Router, Routes, Route, Link, Navigate } from "react-router-dom";
import HomePage from "./modules/Macronutritions/views/HomePage";
import DietPage from "./modules/Macronutritions/views/DietPage";
import MicroPage from "./modules/micronutritions/views/MicroPage";
import AccountView from "./modules/account/views/AccountView";
import BmiView from "./modules/bmi/views/BmiView";
import StatsView from "./modules/stats/views/StatsView";

function App() {
  return (
    <Router>
      <div className="min-h-screen bg-gradient-to-r from-indigo-900 via-purple-900 to-pink-700">
        <div className="navbar bg-gradient-to-r from-indigo-800 via-purple-800 to-pink-700 shadow-md px-6">
          <div className="flex-1">
            <Link
              to="/"
              className="text-4xl font-extrabold tracking-wide text-gray-300 hover:text-white transition"
            >
              FITNESS GOAL ROAD
            </Link>

            <ul className="menu menu-horizontal px-6 gap-4">
              <li>
                <Link
                  to="/diet"
                  className="text-xl font-bold text-gray-400 hover:text-white transition"
                >
                  Diet Goal Road
                </Link>
              </li>

              <li>
                <Link
                  to="/micro"
                  className="text-xl font-bold text-gray-400 hover:text-white transition"
                >
                  Micro Goal Road
                </Link>
              </li>

              <li>
                <Link
                  to="/account"
                  className="text-xl font-bold text-gray-400 hover:text-white transition"
                >
                  Account
                </Link>
              </li>

              <li>
                <Link
                  to="/bmi"
                  className="text-xl font-bold text-gray-400 hover:text-white transition"
                >
                  BMI
                </Link>
              </li>

              <li>
                <Link
                  to="/stats"
                  className="text-xl font-bold text-gray-400 hover:text-white transition"
                >
                  Stats
                </Link>
              </li>
            </ul>
          </div>

          <div className="flex-none">
            <button className="btn btn-ghost text-black text-2xl font-bold border-b-2 border-black rounded-none px-0 hover:bg-transparent">
              Sign In
            </button>
          </div>
        </div>

        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/diet" element={<DietPage />} />
          <Route path="/micro" element={<MicroPage />} />
          <Route path="/account" element={<AccountView />} />
          <Route path="/bmi" element={<BmiView />} />
          <Route path="/stats" element={<StatsView />} />
          <Route path="*" element={<Navigate to="/" replace />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;