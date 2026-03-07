import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Navbar from './modules/Macronutritions/components/Navbar';
import HomePage from './modules/Macronutritions/views/HomePage';
import DietPage from './modules/Macronutritions/views/DietPage';
import MicroPage from './modules/micronutritions/views/MicroPage';

function App() {
    return (
        <Router>
            <div className="min-h-screen">
                <Navbar />
                <Routes>
                    <Route path="/" element={<HomePage />} />
                    <Route path="/diet" element={<DietPage />} />
                    <Route path="/micro" element={<MicroPage />} />
                    <Route path="*" element={<Navigate to="/" replace />} />
                </Routes>
            </div>
        </Router>
    );
}

export default App;

