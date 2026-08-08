const IngredientsList = ({ plan, onBack }) => {
    return (




        <div className="card bg-gradient-to-br from-base-100 to-base-200 shadow-2xl border-4" style={{ borderColor: '#10b981' }}>


            <div className="card-body">
                <h2 className="card-title text-4xl font-black mb-2" style={{ color: '#10b981' }}>Your Ingredients Plan</h2>


                <div className="grid grid-cols-2 md:grid-cols-4 gap-4 mb-8">

                    <div className="stat bg-gradient-to-br from-secondary to-secondary-focus rounded-xl shadow-xl text-white">
                        <div className="stat-title text-white text-sm font-bold opacity-80">Proteins</div>
                        <div className="stat-value text-3xl font-black">{plan.totalProteines.toFixed(1)}g</div>
                    </div>

                </div>


                <table className="table">
                    <thead><tr><th>Ingredient</th><th>Quantity</th><th>Proteins</th><th>Carbs</th><th>Fats</th><th>Calories</th></tr></thead>
                    <tbody>
                        {plan.ingredients.map((item) => (
                            <tr key={item.nom}>
                                <td className="font-bold">{item.nom}</td>
                                <td>{item.grammes.toFixed(0)} g</td>
                                <td>{item.proteines.toFixed(1)} g</td>
                                <td>{item.glucides.toFixed(1)} g</td>
                                <td>{item.lipides.toFixed(1)} g</td>
                                <td>{item.calories.toFixed(0)} kcal</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>


        </div>
    );
};
export default IngredientsList;
