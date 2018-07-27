package msahil432.click_away.intro;

import msahil432.click_away.database.Institute;
import msahil432.click_away.database.MyDao;

import static msahil432.click_away.listActivity.base.BaseActivity.Types.BloodBanks;
import static msahil432.click_away.listActivity.base.BaseActivity.Types.Chemists;
import static msahil432.click_away.listActivity.base.BaseActivity.Types.Hospitals;

public class AddDummyData implements Runnable {

    MyDao dao;
    AddDummyData(MyDao dao){
        this.dao = dao;
    }

    @Override
    public void run() {
        addHospitals();
        addChemists();
        addBloodBanks();
    }

    private void addHospitals(){
        Institute i;
        i= new Institute(
                "589e3face8b57c00119a3f2c",
                "Dr. Baba Saheb Ambedkar Hospital",
                "Sector 6, Rohini, Near Rohini West Metro Station, Sector 6, Rohini, Delhi, 110085",
                "911127055585",
                Hospitals.dbField,
                28.714607,
                77.113459
        );
        dao.save(i);
        i= new Institute(
                "589e6100ec0ba50011eac443",
                "ESI Hospital",
                "Opp HP Petrol Pump, Sector 15A, Rohini, New Delhi, Delhi- 110085",
                "911127861033",
                Hospitals.dbField,
                28.714607,
                77.1235585

        );
        dao.save(i);
        i= new Institute(
                "589e6100ec0ba50011eac442",
                "Aastha Hospital",
                "Badli Rd, Sector 19, Rohini, New Delhi, Delhi 110042",
                "911147903333",
                Hospitals.dbField,
                28.7327251,
                77.0717018
        );
        dao.save(i);
        i= new Institute(
                "589e6100ec0ba50011eac440",
                "Ishan Hospital",
                "Plot No.1, Pocket-8B, Dr MC Davar Marg, Sector 19, Rohini, Delhi, 110089",
                "911127892798",
                Hospitals.dbField,
                28.733512,
                77.0696299
        );
        dao.save(i);
        i= new Institute(
                "589e6100ec0ba50011eac43e",
                "Saroj Super Speciality Hospital",
                "Near Madhuban Chowk, Sector 14, Rohini, Delhi, 110085",
                "911147903333",
                Hospitals.dbField,
                28.7308195,
                77.1310776
        );
        dao.save(i);
        i= new Institute(
                "589e6100ec0ba50011eac43d",
                "Sunrise Hospital",
                "8-B, Plot No-1, B4 Rd, Sector 15, Pocket 1, Rohini, Delhi, 110089",
                "911127894780",
                Hospitals.dbField,
                28.7308195,
                77.1310776
        );
        dao.save(i);
        i= new Institute(
                "589e6100ec0ba50011eac444",
                "Health Hospital",
                "Delhi Technological University, Shahbad Daulatpur Village, Rohini, Delhi, 110042",
                "911127272727",
                Hospitals.dbField,
                28.7471097,
                77.0470234
        );
        dao.save(i);
        i= new Institute(
                "589e6100ec0ba50011eac441",
                "Nirvana Hospital",
                "42, Badli Rd, Sector 15, Pocket 1, Sector 15C, Rohini, Delhi, 110089",
                "911149055088",
                Hospitals.dbField,
                28.710125,
                77.1785709
        );
        dao.save(i);
        i= new Institute(
                "589e6100ec0ba50011eac43f",
                "Brahm Shakti Hospital",
                "No.U-1\\/hospital, Budh Vihar, New Delhi, Delhi 110086",
                "911127531386",
                Hospitals.dbField,
                28.7123525,
                77.0112063
        );
        dao.save(i);
    }

    private void addChemists(){
        Institute i;
        i= new Institute(
                "589e9961b8aa850011d575ec",
                "Medical shoppe",
                "Pocket A, Block E, Sector 16E, Rohini, Delhi, 110089",
                "918527228188",
                Chemists.dbField,
                28.7392456,
                77.1200085
        );
        dao.save(i);
        i= new Institute(
                "589e9961b8aa850011d575ed",
                "Durga Medicos",
                "Shop No 12, CSC No 7, DDA Market, Sec-16, Block F, Sector 16F, Rohini, Delhi, 110085",
                "919211584187",
                Chemists.dbField,
                28.736066,
                77.1206316
        );
        dao.save(i);
        i= new Institute(
                "589e9961b8aa850011d575ee",
                "Chitra Medicos",
                "A-2\\/125, Pocket 2, Sector 16, Rohini, Delhi, 110089",
                "911127892798",
                Chemists.dbField,
                28.7343015,
                77.1220617
        );
        dao.save(i);
        i= new Institute(
                "589e9961b8aa850011d575f1",
                "Oberoi Medicos",
                "A-5\\/25, Dr KN Katju Marg, Pocket 5, Sector 16A, Rohini, Delhi, 110089",
                "911127861033",
                Chemists.dbField,
                28.7270555,
                77.1235585
        );
        dao.save(i);
        i= new Institute(
                "589e9961b8aa850011d575f0",
                "Charchika Medicos",
                "A-4\\/75, Dr KN Katju Marg, Pocket 4, Sector 16A, Rohini, Delhi, 110089",
                "911127299942",
                Chemists.dbField,
                28.7329475,
                77.1251437
        );
        dao.save(i);
        i= new Institute(
                "589e9961b8aa850011d575ef",
                "Apolo Pharmacy",
                "F-18\\/59, Sector 15, Sector 15B, Rohini, Delhi, 110085",
                "911800500101",
                Chemists.dbField,
                28.7328459,
                77.1253318
        );
        dao.save(i);
        i= new Institute(
                "589e9961b8aa850011d575eb",
                "Akash Medicos",
                "G-5, L.S.C 10, Near Canara Bank ATM, Giani Gurumukhi Musaphir Marg, G Block, Sector 16, Rohini, Delhi, 110089",
                "919711147224",
                Chemists.dbField,
                28.7120974,
                77.1413259
        );
        dao.save(i);
    }

    private void addBloodBanks(){
        Institute i;
        i= new Institute(
                "589eabf2c0ed43001120ac9f",
                "Pitampura Blood Bank",
                "B-294, SARASWATI VIHAR, OUTER RING ROAD , ABOVE YAMAHA SHOWROOM ,PITAMPURA, Block B, Saraswati Vihar, Pitampura, Delhi, 110034",
                "91165655155",
                BloodBanks.dbField,
                28.7357424,
                77.0984223
        );
        dao.save(i);
        i= new Institute(
                "589eabf2c0ed43001120ac9d",
                "Rajiv Gandhi Cancer Institute & Research Institute",
                "Sector 5, Rohini, Delhi, 110085",
                "911147022222",
                BloodBanks.dbField,
                28.7164723,
                77.108741
        );
        dao.save(i);
        i= new Institute(
                "589eabf2c0ed43001120ac9a",
                "Dr. Baba Saheb Ambedkar Hospital",
                "Sector 6, Rohini, Near Rohini West Metro Station, Delhi, 110085",
                "911127055585",
                BloodBanks.dbField,
                28.7146069,
                77.1112703
        );
        dao.save(i);
        i= new Institute(
                "589eabf2c0ed43001120ac99",
                "Brahm Shakti Hospital",
                "No.U-1\\/hospital, Budh Vihar, Pocket C, Budh Vihar Phase I, Budh Vihar, New Delhi, Delhi 110086",
                "911127531683",
                BloodBanks.dbField,
                28.7123705,
                77.0790579
        );
        dao.save(i);
        i= new Institute(
                "589eabf2c0ed43001120ac9c",
                "Jaipur Golden Hospital",
                "Plot No.2, Yog Ashram Marg, Sector 3, Institutional Area, Rohini, Institutional Area, Sector 3, Rohini, Delhi, 110085",
                "918888888888",
                BloodBanks.dbField,
                28.6977129,
                77.1075667
        );
        dao.save(i);
        i= new Institute(
                "589eabf2c0ed43001120ac9b",
                "Sanjay Gandhi Memorial Hospital",
                "Block S, Mangolpuri, Mangolpuri, Delhi, 110083",
                "911127922843",
                BloodBanks.dbField,
                28.693597,
                77.0808462
        );
        dao.save(i);
        i= new Institute(
                "589eabf2c0ed43001120ac98",
                "Saroj Super Speciality Hospital",
                "Near Madhuban Chowk, Sector 14, Rohini, Block A, Sector 14, Rohini, Delhi, 110085",
                "911147903333",
                BloodBanks.dbField,
                28.706112,
                77.1413259
        );
        dao.save(i);
        i= new Institute(
                "589eabf2c0ed43001120ac9e",
                "Sunder Lal Charitable Hospital",
                "Phase 3, Ashok Vihar, Delhi, 110052",
                "911147030900",
                BloodBanks.dbField,
                28.6917859,
                77.1792541
        );
        dao.save(i);
    }
}
