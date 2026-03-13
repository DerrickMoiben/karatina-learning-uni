function copy(id){

let text = document.getElementById(id).innerText

navigator.clipboard.writeText(text)

alert("Address copied")

}

function toggleMenu(){

let menu = document.getElementById("dropdown")

menu.style.display = menu.style.display === "block" ? "none" : "block"

}

function randomNotification(){

let names = ["John","David","Maria","Alex","Sophia","Daniel"]

let amounts = ["$120","$350","$50","$900","$200"]

setInterval(()=>{

let name = names[Math.floor(Math.random()*names.length)]

let amount = amounts[Math.floor(Math.random()*amounts.length)]

let popup = document.getElementById("popup")

popup.innerText = name + " deposited " + amount

popup.style.display="block"

setTimeout(()=>{

popup.style.display="none"

},3000)

},8000)

}
