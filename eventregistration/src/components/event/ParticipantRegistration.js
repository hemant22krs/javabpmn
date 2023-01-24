import { useState, useRef } from 'react';
import { useNavigate } from 'react-router-dom';
import classes from './ParticipantRegistration.module.css';

const isEmpty = (value) => value.trim() === '';

const ParticipantRegistration = () => {

    const [formInputsValidity, setFormInputsValidity] = useState({
        email: true,
        firstName: true,
        lasttName: true,
        dob: true,
        address: true,
        city: true,
        state: true,
        zipcode: true
      })

    const navigate = useNavigate();
    let cities = [
        { label: "Pune", value: "Pune" },
        { label: "Bangalore", value: "Bangalore" },
        { label: "Mumbai", value: "Mumbai" }
      ];

    const [selectedState, setSelectedState] = useState('');
    const [enteredDate, setEnteredDate] = useState('');

    const emailInputRef = useRef();
    const firstNameInputRef = useRef();
    const lastNameInputRef = useRef();
    const addressInputRef = useRef();
    const cityInputRef = useRef();
    const zipcodeRef = useRef();

    const switchAuthModeHandler = () => {
     navigate("/displayRegisteredEvent");
      };

    const submitHandler = (event) => {
        event.preventDefault();
        const enteredEmail = emailInputRef.current.value;
        const enteredFirstName = firstNameInputRef.current.value;
        const enteredLastName = lastNameInputRef.current.value;
        const dob = new Date(enteredDate);
        const enteredAddress = addressInputRef.current.value;
        const enteredCity = cityInputRef.current.value;
        const state = selectedState;
        const zipcode = zipcodeRef.current.value;

        const enteredEmailIsValid = !isEmpty(enteredEmail);
        const enteredFirstNameIsValid = !isEmpty(enteredFirstName);
        const enteredLastNameIsValid = !isEmpty(enteredLastName);
        const dobIsValid = !isEmpty(dob);
        const enteredAddresslIsValid = !isEmpty(enteredAddress);
        const enteredCityIsValid = !isEmpty(enteredCity);
        const stateIsValid = !isEmpty(state);
        const zipcodeIsValid = !isEmpty(zipcode);
    
        setFormInputsValidity({
          email: enteredEmailIsValid,
          firstName: enteredFirstNameIsValid,
          lastName: enteredLastNameIsValid,
          dob: dobIsValid,
          address: enteredAddresslIsValid,
          city: enteredCityIsValid,
          state: stateIsValid,
          zipcode: zipcodeIsValid
        });
    
        const formIsValid =
        enteredEmailIsValid &&
        enteredFirstNameIsValid &&
        enteredLastNameIsValid &&
        dobIsValid &&
        enteredAddresslIsValid && 
        enteredCityIsValid &&
        stateIsValid && 
        zipcodeIsValid;
    
        if (!formIsValid) {
          return;
        }
    }

    let handleStateChange = (e) => {
        setSelectedState(e.target.value);
      };

    const dateChangeHandler = (event) => {
        setEnteredDate(event.target.value);
      };

    return(
    <section className={classes.auth}>
      <h1>{'Event Registration'}</h1>
      <form onSubmit={submitHandler}>
        <div className={classes.control}>
          <label htmlFor='email'>Your Email</label>
          <input type='email' id='email' required ref={emailInputRef} />
          {!formInputsValidity.email && <p>Please enter a valid Email!</p>}
        </div>

        <div className={classes.control}>
          <label htmlFor='firstName'>Your First Name</label>
          <input type='text' id='firstName' required ref={firstNameInputRef}  />
          {!formInputsValidity.firstName && <p>Please enter a valid  First Name!</p>}
        </div>

        <div className={classes.control}>
          <label htmlFor='lastName'>Your Last Name</label>
          <input type='text' id='lastName' required  ref={lastNameInputRef} />
        {!formInputsValidity.lasttName && <p>Please enter a valid  Last Name!</p>}
        </div>

        <div className={classes.control}>
        <label htmlFor='dob'>Your Date Of Birth</label>
        <input type='date' min='2019-01-01' max='2022-12-31' onChange={dateChangeHandler} 
         placeholder='Enter BirthDate'  name='birthdate'  />
        {!formInputsValidity.dob && <p>Please enter a valid Date Of Birth!</p>}
        </div>

        <div className={classes.control}>
        <label htmlFor='address'>Your Address</label>
        <input type='text' id='address' required ref={addressInputRef}  />
        {!formInputsValidity.address && <p>Please enter a valid Address!</p>}
        </div>

        <div className={classes.control}>
        <label htmlFor='city'>Your City</label>
        <input type='text' id='city' required ref={cityInputRef}  />
        </div>

        <div className={classes.control}>
        <label htmlFor='state'>Your state</label>
        <select onChange={handleStateChange} >
        <option value="⬇️ Select a State ⬇️"> -- Select a fruit -- </option>
        {cities.map((city) => (
          <option value={cities.value}>{cities.label}</option>
        ))}
      </select>

      <label htmlFor='zipcode'>Your Zipcode </label>
      <input type='number' id='zipcode' style={{width: "10rem"}}  required ref={zipcodeRef}  />
      </div>

      <div className={classes.actions}>
      <button className={classes.button}>Submit</button>
      <button type='button' className={classes.toggle}  onClick={switchAuthModeHandler}>
        {'Check Existing Registered Event'}
      </button>
    
      </div>
      </form>
    </section>
    );

};

export default ParticipantRegistration;