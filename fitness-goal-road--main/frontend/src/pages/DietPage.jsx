import { useState } from 'react';
import QuizForm from '../components/QuizForm';
import ResultDisplay from '../components/ResultDisplay';
import AdjustmentForm from '../components/AdjustmentForm';
import { calculateNutrition, adjustNutrition } from '../utils/api';

const DietPage = () => {
    const [step, setStep] = useState('auth');
    const [loading, setLoading] = useState(false);
    const [calculationResult, setCalculationResult] = useState(null);
    const [adjustmentResult, setAdjustmentResult] = useState(null);
    const [userData, setUserData] = useState(null);

    const handleContinueAsGuest = () => {
        setStep('quiz');
    };

    const handleQuizSubmit = async (formData) => {
        setLoading(true);
        try {
            const result = await calculateNutrition(formData);
            setCalculationResult(result);
            setUserData(formData);
            setStep('result');
        } catch (error) {
            alert('Error calculating nutrition. Please try again.');
            console.error(error);
        } finally {
            setLoading(false);
        }
    };

    const handleAdjustClick = () => {
        setStep('adjustment');
    };

    const handleAdjustmentSubmit = async (data) => {
        setLoading(true);
        try {
            const result = await adjustNutrition(data);
            setAdjustmentResult(result);
        } catch (error) {
            alert('Error adjusting nutrition. Please try again.');
            console.error(error);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="min-h-screen bg-gradient-to-br from-base-300 via-base-200 to-base-100 py-12">
            <div className="container mx-auto px-4 max-w-5xl">
                {step === 'auth' && (
                    <div className="card bg-gradient-to-br from-base-100 to-base-200 shadow-2xl border-4 border-primary">
                        <div className="card-body text-center">
                            <h2 className="card-title text-5xl font-black justify-center mb-6 text-primary">
                                Diet Goal Road
                            </h2>
                            <div className="alert alert-warning shadow-xl mb-6">
                                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" className="stroke-current shrink-0 w-8 h-8">
                                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z" />
                                </svg>
                                <span className="text-xl font-bold">You are not connected</span>
                            </div>
                            <p className="text-xl font-semibold mb-8">
                                Sign in to save your progress or continue as a guest to get started immediately.
                            </p>
                            <div className="card-actions justify-center gap-4">
                                <button className="btn btn-primary btn-lg text-xl font-black" disabled>
                                    Sign In
                                </button>
                                <button
                                    className="btn btn-secondary btn-lg text-xl font-black shadow-xl hover:scale-105 transition-transform"
                                    onClick={handleContinueAsGuest}
                                >
                                    Continue as Guest
                                </button>
                            </div>
                        </div>
                    </div>
                )}

                {step === 'quiz' && (
                    <QuizForm onSubmit={handleQuizSubmit} loading={loading} />
                )}

                {step === 'result' && calculationResult && (
                    <ResultDisplay
                        result={calculationResult}
                        onAdjust={handleAdjustClick}
                    />
                )}

                {step === 'adjustment' && (
                    <AdjustmentForm
                        currentWeight={userData.poids}
                        goal={userData.goal}
                        tdee={calculationResult.tdee}
                        onSubmit={handleAdjustmentSubmit}
                        loading={loading}
                        adjustmentResult={adjustmentResult}
                    />
                )}
            </div>
        </div>
    );
};

export default DietPage;