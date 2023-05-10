import logo from './logo.svg';
import './App.css';
import Appointment from './components/Appointment';
import Grid from './components/Grid';

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
        <Grid/>
        <Appointment/>
    </div>
  );
}

export default App;
