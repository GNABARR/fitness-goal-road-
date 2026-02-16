import { useState, useEffect } from 'react';

function App() {
  const [page, setPage] = useState('form'); 
  const [userId, setUserId] = useState(null);
  const [userData, setUserData] = useState(null);
  const [nutritionData, setNutritionData] = useState(null);
  const [error, setError] = useState(null);

  
  const getActivityLevelValue = (label) => {
    const mapping = {
      'Sédentaire': 1.2,
      'Légèrement actif': 1.375,
      'Modérément actif': 1.55,
      'Très actif': 1.725,
      'Extrêmement actif': 1.9
    };
    return mapping[label] || 1.55; 
  };

  
  useEffect(() => {
    if (page === 'result' && userId) {
      fetch(`http://localhost:8080/api/users/${userId}`)
        .then(res => res.json())
        .then(setUserData)
        .catch(err => console.error(err));

      fetch(`http://localhost:8080/api/nutrition/${userId}`)
        .then(res => res.json())
        .then(setNutritionData)
        .catch(err => console.error(err));
    }
  }, [page, userId]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError(null);
    
    const formData = new FormData(e.target);
    const activityLevelLabel = formData.get('activityLevel');
    const activityLevelValue = getActivityLevelValue(activityLevelLabel);
    
    const user = {
      firstName: formData.get('firstName'),
      lastName: formData.get('lastName'),
      email: formData.get('email'),
      password: formData.get('password'),
      dateOfBirth: formData.get('dateOfBirth'),
      gender: formData.get('gender'),
      weight: parseFloat(formData.get('weight')),
      height: parseFloat(formData.get('height')),
      activityLevel: activityLevelValue, 
      goal: formData.get('goal')
    };

    try {
      const res = await fetch('http://localhost:8080/api/users', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(user)
      });

      if (!res.ok) {
        const errorData = await res.json().catch(() => null);
        throw new Error(errorData?.message || 'Erreur serveur');
      }
      
      const savedUser = await res.json();
      setUserId(savedUser.id);
      setPage('result');
    } catch (err) {
      setError('Erreur : ' + err.message);
    }
  };

  if (page === 'result') {
    if (!userData || !nutritionData) return <div>Chargement...</div>;

    return (
      <div style={{ padding: '20px', fontFamily: 'Arial' }}>
        <h2>Vos Informations</h2>
        <p><strong>Nom :</strong> {userData.firstName} {userData.lastName}</p>
        <p><strong>Email :</strong> {userData.email}</p>
        <p><strong>Date de naissance :</strong> {userData.dateOfBirth}</p>
        <p><strong>Genre :</strong> {userData.gender}</p>
        <p><strong>Poids :</strong> {userData.weight} kg</p>
        <p><strong>Taille :</strong> {userData.height} cm</p>
        <p><strong>Objectif :</strong> {userData.goal}</p>

        <h2>Vos Objectifs Nutritionnels</h2>
        <p><strong>Calories :</strong> {nutritionData.calories} kcal</p>
        <p><strong>Protéines :</strong> {nutritionData.protein} g</p>
        <p><strong>Glucides :</strong> {nutritionData.carbs} g</p>
        <p><strong>Lipides :</strong> {nutritionData.fats} g</p>
        <p><strong>BMR :</strong> {nutritionData.bmr} | <strong>TDEE :</strong> {nutritionData.tdee}</p>

        <button onClick={() => setPage('form')} style={{
          marginTop: '20px',
          padding: '10px 20px',
          backgroundColor: '#4CAF50',
          color: 'white',
          border: 'none',
          cursor: 'pointer'
        }}>
          Nouveau calcul
        </button>
        
        {error && <div style={{color: 'red', marginTop: '10px'}}>{error}</div>}
      </div>
    );
  }

  return (
    <div style={{ padding: '20px', fontFamily: 'Arial' }}>
      
      {error && <div style={{color: 'red', marginBottom: '10px'}}>{error}</div>}
      
      <form onSubmit={handleSubmit}>
        <div style={{ marginBottom: '10px' }}>
          <label>Prénom: <input name="firstName" required /></label>
        </div>
        <div style={{ marginBottom: '10px' }}>
          <label>Nom: <input name="lastName" required /></label>
        </div>
        <div style={{ marginBottom: '10px' }}>
          <label>Email: <input name="email" type="email" required /></label>
        </div>
        <div style={{ marginBottom: '10px' }}>
          <label>Mot de passe: <input name="password" type="password" required /></label>
        </div>
        <div style={{ marginBottom: '10px' }}>
          <label>Date de naissance: <input name="dateOfBirth" type="date" required /></label>
        </div>
        <div style={{ marginBottom: '10px' }}>
          <label>Genre:
            <select name="gender" required>
              <option value="male">Homme</option>
              <option value="female">Femme</option>
            </select>
          </label>
        </div>
        <div style={{ marginBottom: '10px' }}>
          <label>Poids (kg): <input name="weight" type="number" step="0.1" required /></label>
        </div>
        <div style={{ marginBottom: '10px' }}>
          <label>Taille (cm): <input name="height" type="number" step="0.1" required /></label>
        </div>
        <div style={{ marginBottom: '10px' }}>
          <label>Niveau d'activité:
            <select name="activityLevel" required>
              <option value="Sédentaire">Sédentaire</option>
              <option value="Légèrement actif">Légèrement actif</option>
              <option value="Modérément actif">Modérément actif</option>
              <option value="Très actif">Très actif</option>
              <option value="Extrêmement actif">Extrêmement actif</option>
            </select>
          </label>
        </div>
        <div style={{ marginBottom: '10px' }}>
          <label>Objectif:
            <select name="goal" required>
              <option value="cutting">Perte de poids</option>
              <option value="maintenance">Maintien</option>
              <option value="bulking">Prise de masse</option>
            </select>
          </label>
        </div>
        <button type="submit" style={{
          padding: '10px 20px',
          backgroundColor: '#4CAF50',
          color: 'white',
          border: 'none',
          cursor: 'pointer'
        }}>
          Calculer mes objectifs
        </button>
      </form>
    </div>
  );
}

export default App;