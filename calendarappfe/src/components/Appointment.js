import { Card, CardContent, Typography, TextField, Stack } from "@mui/material";

const Appointment = ({appointment}) => {
    return (
        <div>
            <Card sx={{ width: '100%', height:'15%' }}>
                <CardContent sx={{ padding: '8px 16px', '&:last-child': {paddingBottom: '10px'} }} >
                    <Stack>
                    <Typography variant="h5" sx={{ mb: 0, float: 'left', fontStyle: 'italic', fontSize: '14pt', border:'none'}} component="div">
                        <TextField id="outlined-search" label="Candidate Name" type="search" sx={{"& fieldset": { border: 'none' }}} 
                        InputLabelProps={{ style: {fontSize: '11pt'} }} inputProps={{style: {paddingBottom: '8px'}}}/>
                    </Typography>
                    <Stack sx={{textAlign: "left"}}>
                    <Typography sx={{ mt: 0, fontSize: '11pt' }} color="text.secondary">
                        <label display="inline-block">Experienced interviewer: </label>
                        <input type="text" style={{border:"none"}}/>
                    </Typography>
                    <Typography color="text.secondary" sx={{ fontSize: '11pt'}}>
                        <label display="inline-block">Inexperienced interviewer: </label>
                        <input type="text" style={{border:"none"}} />
                    </Typography>
                    </Stack>
                    </Stack>
                </CardContent>
            </Card>
        </div>
    );
};

export default Appointment;