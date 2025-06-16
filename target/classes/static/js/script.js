

let currenttheater = gettheme();


changetheme()

function changetheme()
{
    document.querySelector("html").classList.add(currenttheater)

    const change_theme = document.querySelector("#theme_change_button")
    change_theme.addEventListener("click", (event) => {
        console.log(currenttheater)

        document.querySelector("html").classList.remove(currenttheater)
        console.log("clicked...")

        if(currenttheater === "dark")
        {
            currenttheater = "light"
        }
        else
        {
            currenttheater = "dark"
        }


        settheme(currenttheater)
    document.querySelector("html").classList.add(currenttheater)


    change_theme.querySelector("span").textContent = currenttheater

    });
    
}

function settheme(theme)
{
    localStorage.setItem("theme",theme)
}

function gettheme()
{
    let theme = localStorage.getItem("theme")
    if(theme)
    {
        return theme

    }else{
        return "light"
    }
}