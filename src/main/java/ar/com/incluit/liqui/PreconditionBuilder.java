package ar.com.incluit.liqui;

import ar.com.incluit.domain.AbstractParameter;
import ar.com.incluit.liqui.changelog.Precondition;
import ar.com.incluit.liqui.changelog.SQLCheck;
import ar.com.incluit.liqui.visitor.IdVisitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class PreconditionBuilder {

    @Autowired
    private TableResolver tableResolver;

    @Value("${schema}")
    private String schemaName;


    public List<Precondition> buildPreconditions(List<? extends AbstractParameter> parameterList) {

        if(parameterList == null || parameterList.isEmpty()){
            return null;
        }

        List<Precondition> preconditions = new ArrayList<>();

        Precondition precondition = new Precondition();
        String tabla = tableResolver.obtenerTabla(parameterList.get(0).getAbstractGrupoTipo());

        StringBuilder sb = new StringBuilder();
        for (AbstractParameter param : parameterList){
            IdVisitor visitor = new IdVisitor();
            param.accept(visitor);

            sb.append(visitor.getId()).append(",");
        }
        String ids = sb.toString().substring(0, sb.length() - 1);


        precondition.setOnFail("HALT");
        precondition.setOnFailMessage("Los registros que se desean insertar en la tabla " + tabla + " ya existem.");
        SQLCheck sqlcheck = new SQLCheck();
        sqlcheck.setExpectedResult("0");
        sqlcheck.setSql("select count(*) from "+schemaName+"."+tabla+" where id in ("+ids+");");
        precondition.setSqlCheck(sqlcheck);

        return Collections.singletonList(precondition);
    }

}
