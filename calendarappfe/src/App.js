import logo from './logo.svg';
import './App.css';
import Appointment from './components/Appointment';

//for testing the Appointment component
// const tmpAppointment = {
//   id: 1,
//   day: new Date(Date.now()),
//   candidate: {
//     id: 2,
//     firstName: 'Pera',
//     lastName: 'Peric',
//     email: 'pera.peric@gmail.com'
//   },
//   interviewers: [{
//     id: 4,
//     firstName: 'Marko',
//     lastName: 'Markovic',
//     email: 'marko.markovic@endava.com',
//     isExperienced: true
//   },
//   {
//     id: 7,
//     firstName: 'Marija',
//     lastName: 'Maric',
//     email: 'marija.maric@endava.com',
//     isExperienced: false
//   }]
// };

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Edit <code>src/App.js</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
        {/* <Appointment appointment={tmpAppointment}/> */}
      </header>
    </div>
  );
}

export default App;
