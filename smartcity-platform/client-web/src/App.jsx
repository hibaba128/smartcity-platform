import { useState, useEffect } from 'react'

const GATEWAY_URL = 'http://localhost:8085/api'

function App() {
  const [activeTab, setActiveTab] = useState('dashboard')
  const [quartier, setQuartier] = useState('centre')
  const [orchestratorResult, setOrchestratorResult] = useState(null)
  const [loading, setLoading] = useState(false)
  const [servicesStatus, setServicesStatus] = useState({
    mobilite: 'Unknown',
    air: 'Unknown',
    gateway: 'Online'
  })

  // Check services health (simulation/simple check)
  useEffect(() => {
    // In a real app we'd ping health endpoints
    // Here we just assume Gateway is reachable if we can load the page
    setServicesStatus(s => ({ ...s, gateway: 'Online' }))
  }, [])

  const handlePlanify = async () => {
    setLoading(true)
    setOrchestratorResult(null)
    try {
      const res = await fetch(`${GATEWAY_URL}/trajet-intelligent?quartier=${quartier}`)
      const data = await res.json()
      setOrchestratorResult(data)
    } catch (e) {
      console.error(e)
      setOrchestratorResult({ error: "Erreur de connexion à l'Orchestrateur" })
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="min-h-screen bg-slate-900 text-white font-sans selection:bg-blue-500 selection:text-white">
      {/* Navbar */}
      <nav className="border-b border-slate-800 bg-slate-900/50 backdrop-blur-md fixed w-full z-10">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex items-center justify-between h-16">
            <div className="flex items-center gap-3">
              <div className="w-8 h-8 bg-gradient-to-br from-blue-500 to-cyan-400 rounded-lg flex items-center justify-center font-bold text-white shadow-lg shadow-blue-500/20">SC</div>
              <span className="font-bold text-xl tracking-tight text-slate-100">SmartCity<span className="text-blue-500">Platform</span></span>
            </div>
            <div className="flex space-x-4">
              {['dashboard', 'services', 'docs'].map(tab => (
                <button
                  key={tab}
                  onClick={() => setActiveTab(tab)}
                  className={`px-3 py-2 rounded-md text-sm font-medium transition-all duration-200 ${activeTab === tab ? 'bg-blue-600 text-white shadow-lg shadow-blue-600/20' : 'text-slate-400 hover:text-white hover:bg-slate-800'}`}
                >
                  {tab.charAt(0).toUpperCase() + tab.slice(1)}
                </button>
              ))}
            </div>
          </div>
        </div>
      </nav>

      {/* Content */}
      <main className="pt-24 pb-12 px-4 max-w-7xl mx-auto">

        {activeTab === 'dashboard' && (
          <div className="space-y-8">
            {/* Hero Section */}
            <div className="text-center mb-12">
              <h1 className="text-4xl md:text-5xl font-extrabold text-transparent bg-clip-text bg-gradient-to-r from-blue-400 to-cyan-300 mb-4">
                Services Urbains Intelligents
              </h1>
              <p className="text-slate-400 text-lg max-w-2xl mx-auto">
                Plateforme interopérable intégrant REST, SOAP, GraphQL et gRPC pour une gestion de ville connectée et réactive.
              </p>
            </div>

            {/* Workflow Widget */}
            <div className="bg-slate-800/50 border border-slate-700 rounded-2xl p-8 backdrop-blur-sm shadow-xl">
              <h2 className="text-2xl font-bold mb-6 flex items-center gap-2">
                <span className="text-3xl">🧭</span> Planificateur de Trajet
              </h2>
              <div className="flex flex-col md:flex-row gap-4 items-end">
                <div className="flex-1 w-full">
                  <label className="block text-sm font-medium text-slate-400 mb-2">Quartier de destination</label>
                  <select
                    value={quartier}
                    onChange={e => setQuartier(e.target.value)}
                    className="w-full bg-slate-900 border border-slate-700 rounded-lg px-4 py-3 text-white focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none transition-all"
                  >
                    <option value="centre">Centre-Ville</option>
                    <option value="nord">Quartier Nord</option>
                    <option value="sud">Quartier Sud</option>
                    <option value="est">Quartier Est</option>
                  </select>
                </div>
                <button
                  onClick={handlePlanify}
                  disabled={loading}
                  className="w-full md:w-auto bg-blue-600 hover:bg-blue-500 text-white px-8 py-3 rounded-lg font-semibold shadow-lg shadow-blue-600/30 transition-all active:scale-95 disabled:opacity-50 disabled:cursor-not-allowed"
                >
                  {loading ? 'Analyse...' : 'Lancer l\'Orchestrateur'}
                </button>
              </div>

              {/* Result Area */}
              {orchestratorResult && (
                <div className="mt-8 grid grid-cols-1 md:grid-cols-2 gap-6 animate-fade-in">
                  {/* Air Quality Card */}
                  <div className={`p-6 rounded-xl border ${orchestratorResult.air?.aqi > 100 ? 'bg-red-900/20 border-red-800' : 'bg-green-900/20 border-green-800'}`}>
                    <h3 className="text-lg font-semibold mb-2 flex items-center gap-2">
                      ☁️ Qualité de l'Air (SOAP)
                    </h3>
                    <div className="text-3xl font-bold mb-1">{orchestratorResult.air?.aqi || 'N/A'} <span className="text-sm font-normal text-slate-400">AQI</span></div>
                    <p className="text-sm opacity-90">{orchestratorResult.air?.recommandation}</p>
                  </div>

                  {/* Mobility Card */}
                  <div className="p-6 rounded-xl bg-slate-700/30 border border-slate-600">
                    <h3 className="text-lg font-semibold mb-2 flex items-center gap-2">
                      🚌 Mobilité (REST)
                    </h3>
                    <div className="space-y-2 text-sm">
                      <div className="flex justify-between border-b border-slate-600 pb-2">
                        <span className="text-slate-400">Trafic</span>
                        <span className="font-medium text-emerald-400">Fluide (Simulé)</span>
                      </div>
                      <pre className="text-xs bg-slate-900 p-2 rounded text-slate-300 overflow-x-auto">
                        {typeof orchestratorResult.mobilite === 'string'
                          ? orchestratorResult.mobilite
                          : JSON.stringify(orchestratorResult.mobilite, null, 2)}
                      </pre>
                    </div>
                  </div>

                  {/* Backend Logs */}
                  <div className="col-span-1 md:col-span-2 mt-4 p-4 bg-black/40 rounded-lg text-xs font-mono text-slate-500">
                    <div className="flex justify-between">
                      <span>Log ID: {orchestratorResult.logId}</span>
                      <span>Status: {orchestratorResult.saved ? 'Sauvegardé en BDD' : 'Erreur sauvegarde'}</span>
                    </div>
                  </div>
                </div>
              )}
            </div>

            {/* Quick Stats Grid */}
            <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
              {[
                { title: 'API Gateway', status: 'Actif', port: '8085', color: 'blue' },
                { title: 'Microservices', count: '4', detail: 'REST, SOAP, GQL, gRPC', color: 'purple' },
                { title: 'Conteneurs', status: 'Dockerized', check: 'Healthy', color: 'emerald' }
              ].map((stat, i) => (
                <div key={i} className={`bg-slate-800/50 p-6 rounded-xl border border-slate-700 border-t-4 border-t-${stat.color}-500 hover:-translate-y-1 transition-transform duration-300`}>
                  <h3 className="text-slate-400 font-medium text-sm uppercase tracking-wider">{stat.title}</h3>
                  <div className="text-2xl font-bold mt-2 text-white">{stat.status || stat.count}</div>
                  <div className={`text-sm mt-1 text-${stat.color}-400`}>{stat.port || stat.detail || stat.check}</div>
                </div>
              ))}
            </div>
          </div>
        )}

        {activeTab === 'services' && (
          <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
            {/* Service Card Template */}
            <div className="bg-slate-800 p-6 rounded-xl border border-slate-700">
              <h3 className="text-xl font-bold text-white mb-4">Météo & Air (SOAP)</h3>
              <p className="text-slate-400 text-sm mb-4">Service Legacy interconnecté via adaptateur SOAP.</p>
              <a href="http://localhost:8085/qualiteair/ws/airquality.wsdl" target="_blank" className="text-blue-400 hover:text-blue-300 text-sm">Voir WSDL &rarr;</a>
            </div>
            {/* Add more cards for other services */}
            <div className="bg-slate-800 p-6 rounded-xl border border-slate-700">
              <h3 className="text-xl font-bold text-white mb-4">Profils Citoyens (GraphQL)</h3>
              <p className="text-slate-400 text-sm mb-4">Gestion flexible des préférences utilisateurs.</p>
              <code className="bg-slate-900 px-2 py-1 rounded text-xs text-purple-400">POST /api/profils/graphql</code>
            </div>
          </div>
        )}

        {activeTab === 'docs' && (
          <iframe src="http://localhost:8085/mobilite/swagger-ui.html" className="w-full h-[80vh] rounded-xl border border-slate-700 bg-white" title="Swagger UI"></iframe>
        )}

      </main>
    </div>
  )
}

export default App
