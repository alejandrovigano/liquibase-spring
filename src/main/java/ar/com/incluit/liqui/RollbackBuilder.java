package ar.com.incluit.liqui;

import ar.com.incluit.domain.AbstractParameter;
import ar.com.incluit.liqui.changelog.Delete;
import ar.com.incluit.liqui.changelog.Precondition;
import ar.com.incluit.liqui.changelog.RollBack;
import ar.com.incluit.liqui.changelog.SQLCheck;
import ar.com.incluit.liqui.visitor.IdVisitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class RollbackBuilder {

    @Autowired
    private TableResolver tableResolver;

    @Value("${schema}")
    private String schemaName;


    public List<RollBack> buildRollBack(List<? extends AbstractParameter> parameterList) {

        if(parameterList == null || parameterList.isEmpty()){
            return null;
        }

        List<RollBack> rollBacks = new ArrayList<>();

        RollBack rollback = new RollBack();
        String tabla = tableResolver.obtenerTabla(parameterList.get(0).getAbstractGrupoTipo());

        StringBuilder sb = new StringBuilder();
        for (AbstractParameter param : parameterList){
            IdVisitor visitor = new IdVisitor();
            param.accept(visitor);

            sb.append(visitor.getId()).append(",");
        }
        String ids = sb.toString().substring(0, sb.length() - 1);


        Delete delete = new Delete();
        delete.setSchemaName(schemaName);
        delete.setTableName(tabla);
        delete.setWhere("id in ("+ids+")");

        rollback.setDelete(delete);

        return Collections.singletonList(rollback);
    }

}
