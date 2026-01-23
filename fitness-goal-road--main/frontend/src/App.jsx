import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Navbar from './components/Navbar';
import HomePage from './pages/HomePage';
import DietPage from './pages/DietPage';

function App() {
    return (
        <Router>
            <div className="min-h-screen">
                <Navbar />
                <Routes>
                    <Route path="/" element={<HomePage />} />
                    <Route path="/diet" element={<DietPage />} />
                </Routes>
            </div>
        </Router>
    );
}

export default App;