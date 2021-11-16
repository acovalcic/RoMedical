/*
RESTFUL Services by NodeJS
Author: Adrian Covalcic
RoMedical
*/

var express = require('express');
var mysql = require('mysql');
var bodyParser = require('body-parser');

//Conectare la MySQL
var con = mysql.createConnection({
    host:'localhost',
    user:'root',
    password:'password',
    database:'medical',
});

//Creare RESTFul
var app = express();
var publicDir=(__dirname+'/public/');
app.use(express.static(publicDir));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended:true}));

//GET Spitale 
app.get("/spital",(req,res,next)=>{

    var query = "call getSpitale"

    con.query(query, function(error,result,fields){
        con.on('error',function(err){
            console.log('[MYSQL]ERROR',err);
        });
        if(result[0] && result[0].length)
        {
            res.json(result[0]);
        }
        else
        {
            res.end(JSON.stringify('Nu sunt spitale'));
        }
    });
});

//Cauta Spital dupa Denumire 
app.post("/search",(req,res,next)=>{

    var post_data = req.body; 
    var name_search = post_data.search; 

    var query = "call searchSpitale('"+name_search+"')"

    con.query(query, function(error,result,fields){
        con.on('error',function(err){
            console.log('[MYSQL]ERROR',err);
        });
        if(result[0] && result[0].length)
        {
            res.json(result[0]);
        }
        else
        {
            res.end(JSON.stringify('Nu sunt spitale'));
        }
    });
});

//GET Judete
app.get("/judet",(req,res,next)=>{

    var query = "SELECT denumire_judet as denumire FROM medical.judete"

    con.query(query, function(error,result,fields){
        con.on('error',function(err){
            console.log('[MYSQL]ERROR',err);
        });
        if(result && result.length)
        {
            res.json(result);
        }
        else
        {
            res.end(JSON.stringify('Nu sunt judete'));
        }
    });
});

//GET Categorii
app.get("/categorie",(req,res,next)=>{

    var query = "SELECT denumire_categorie as denumire FROM medical.categorii_spital"

    con.query(query, function(error,result,fields){
        con.on('error',function(err){
            console.log('[MYSQL]ERROR',err);
        });
        if(result && result.length)
        {
            res.json(result);
        }
        else
        {
            res.end(JSON.stringify('Nu sunt categorii'));
        }
    });
});

//Cauta Spital dupa Judet si Categorie 
app.post("/spitaljudcat",(req,res,next)=>{

    var post_data = req.body; 
    var judet = post_data.judet;
    var categorie = post_data.categorie; 

    var query = "call searchSpitalByJudAndCat('"+judet+"', '"+categorie+"')"

    con.query(query, function(error,result,fields){
        con.on('error',function(err){
            console.log('[MYSQL]ERROR',err);
        });
        if(result[0] && result[0].length)
        {
            res.json(result[0]);
        }
        else
        {
            res.end(JSON.stringify('Nu sunt spitale'));
        }
    });
});

//POST Feedback
app.post("/feedback",(req,res,next)=>{

    var post_data = req.body;
    var p_spital = post_data.p_spital;
    var p_doctor = post_data.p_doctor; 
    var p_nota_spital = post_data.p_nota_spital;
    var p_nota_doctor = post_data.p_nota_doctor; 
    var p_mentiuni = post_data.p_mentiuni; 

    var query = "call inserareFeedback('"+p_spital+"', '"+p_doctor+"', '"+p_nota_spital+"', '"+p_nota_doctor+"', '"+p_mentiuni+"')"

    con.query(query, function(error,result,fields){
        if(error) throw error;
        res.json(result);
    });
});

//GET Clasament Doctori
app.get("/cladoc",(req,res,next)=>{

    var query = "call clasamentDoctori"

    con.query(query, function(error,result,fields){
        con.on('error',function(err){
            console.log('[MYSQL]ERROR',err);
        });
        if(result[0] && result[0].length)
        {
            res.json(result[0]);
        }
        else
        {
            res.end(JSON.stringify('Nu sunt doctori'));
        }
    });
});

//GET Clasament Spitale
app.get("/claspi",(req,res,next)=>{

    var query = "call clasamentSpitale"

    con.query(query, function(error,result,fields){
        con.on('error',function(err){
            console.log('[MYSQL]ERROR',err);
        });
        if(result[0] && result[0].length)
        {
            res.json(result[0]);
        }
        else
        {
            res.end(JSON.stringify('Nu sunt spitale'));
        }
    });
});

//Start Server: node index.js
app.listen(3000, ()=>{
    console.log('RoMedical API running on port 3000');
})