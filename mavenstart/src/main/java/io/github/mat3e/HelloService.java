package io.github.mat3e;

import java.util.Optional;

public class HelloService {
    static final String FALLBACK_NAME="world";
    static final Lang FALLBACK_LANG= new Lang(1,"Hello", "en");

    private LangRepository repository;

    HelloService(){
        this(new LangRepository());
    }

    HelloService(LangRepository repository){
        this.repository = repository;
    }

    String prepareGreeting(String name){
        return prepareGreeting(name,null);
    }

    String prepareGreeting(String name, String lang)
    {
        Integer langId;
       try{    langId =Optional.ofNullable(lang).map(Integer::valueOf).orElse(FALLBACK_LANG.getId()); //zabezpieczenie lang przed byciem nullem
       }catch(NumberFormatException e){
           langId=FALLBACK_LANG.getId();

       }
        var welcomeMsg = repository.findById(langId).orElse(FALLBACK_LANG).getWelcomeMsg(); //zabezpieczenie w przypadku braku takiego id
        var nameToWelcome = Optional.ofNullable(name).orElse(FALLBACK_NAME); //zabezpieczenie czy name nie jest nullem
        return welcomeMsg+" "+nameToWelcome+"!";
    }
}
