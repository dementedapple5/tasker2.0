package model;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.SwingUtilities;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpResponse;



public class Connector {


	static CloseableHttpClient httpclient = HttpClients.createDefault();


	//Devuelve un ArrayList con todos los username registrados
	public ArrayList<User> obtenerUsuarios() throws ClientProtocolException, IOException{

            ArrayList<User> listUser = new ArrayList<>();
            try {
                    HttpGet httpGet = new HttpGet("https://centraldemascotas.com/aplicaciones/tasker/select_users.php");
                    CloseableHttpResponse response1 = httpclient.execute(httpGet);

                    try {
                            HttpEntity entity1 = response1.getEntity();
                            String json = IOUtils.toString(entity1.getContent(), "UTF8");        
                            Gson gson = new Gson(); // Or use new GsonBuilder().create();
                            Type collectionType = new TypeToken<Collection<UserPojo>>(){}.getType();
                            List<UserPojo> usuarios = (List<UserPojo>) new Gson()
                                            .fromJson( json , collectionType);

                            for(UserPojo user: usuarios) {
                                    //System.out.println(user.getUsername());
                                    listUser.add(new User(user.getUsername(),user.getPassword()));
                                    //System.out.println(listUser.toString());
                            }

                            EntityUtils.consume(entity1);
                    } finally {

                    }


            } finally {

            }
            return listUser;
	}
	
	public boolean registerUser(String name, String username, String password) throws IOException {
                
            HttpPost httpPost = new HttpPost("https://centraldemascotas.com/aplicaciones/tasker/create_user.php");
            List<NameValuePair> user = new ArrayList<NameValuePair>();
            user.add(new BasicNameValuePair("username",username));
            user.add(new BasicNameValuePair("pass",password));
            user.add(new BasicNameValuePair("name",name));
            user.add(new BasicNameValuePair("token",""));
            System.out.println(user);

            ArrayList<User> usuarios =  obtenerUsuarios();

            try {
                for (User usuario: usuarios){
                    if (username.equals(usuario.getUsername())) return false;
                }

                httpPost.setEntity(new UrlEncodedFormEntity(user));
                CloseableHttpResponse response = httpclient.execute(httpPost);
                StatusLine status = response.getStatusLine();
                System.out.println(status);

            } catch (IOException e) {
                    System.out.println("Fallo de Conexion");
            }
            return true;
	}
        
        public boolean addTask(String title, String comment, String desc, int prior, String username) {
            HttpPost httpPost = new HttpPost("https://centraldemascotas.com/aplicaciones/tasker/insert_tasks.php");

            Task tarea = new Task(title, username, comment, desc, prior);
            boolean found = false;

            List<NameValuePair> task = new ArrayList<NameValuePair>();
            task.add(new BasicNameValuePair("titulo",tarea.getTitle()));
            task.add(new BasicNameValuePair("coments",tarea.getComment()));
            task.add(new BasicNameValuePair("contenido",tarea.getDescription()));
            task.add(new BasicNameValuePair("encargado",tarea.getAttendant()));
            task.add(new BasicNameValuePair("prioridad",String.valueOf(tarea.getPriority())));
            task.add(new BasicNameValuePair("estado",String.valueOf(tarea.isState())));
            task.add(new BasicNameValuePair("visible",String.valueOf(tarea.isVisible())));
            task.add(new BasicNameValuePair("fecha",tarea.getCreationDate()));
            System.out.println(task);
            try {
                    for (Task t: obtenerTareas(tarea.getAttendant())) {
                        if (t.getTitle().equalsIgnoreCase(tarea.getTitle())) found = true;
                    }

                    if(!found) {
                            httpPost.setEntity(new UrlEncodedFormEntity(task));
                            CloseableHttpResponse response = httpclient.execute(httpPost);
                            StatusLine status = response.getStatusLine();
                            System.out.println(status);
                    }
            } catch (IOException e) {
                    System.out.println("Fallo de Conexion");
            }

            return found;
	}
        
        
	
	public boolean checkUser(String username, String pass) {
		HttpPost httpPost = new HttpPost("https://centraldemascotas.com/aplicaciones/tasker/check_user.php");
		boolean found = false;
		try {
			List<NameValuePair> user = new ArrayList<NameValuePair>();
			user.add(new BasicNameValuePair("username",username));
                        user.add(new BasicNameValuePair("pass",pass));
                        httpPost.setEntity(new UrlEncodedFormEntity(user));
		    
                        CloseableHttpResponse response1 = httpclient.execute(httpPost);
			HttpEntity entity1 = response1.getEntity();
			String json = IOUtils.toString(entity1.getContent(), "UTF8");
                        System.out.println("json: "+json);
			if (json.equals("1")) {
				found=true;
			}else {
				found = false;
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		
		return found;
	}

	public List<Task> obtenerTareas(String encargado) throws ClientProtocolException, IOException{


		List<Task> listTareas = new ArrayList<>();

		HttpPost httpPost = new HttpPost("https://centraldemascotas.com/aplicaciones/tasker/select_tasks.php");
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
	    params.add(new BasicNameValuePair("encargado", encargado));
	    httpPost.setEntity(new UrlEncodedFormEntity(params));
	 
	    CloseableHttpResponse response = httpclient.execute(httpPost);
	    HttpEntity entity1 = response.getEntity();
	    String json2 = IOUtils.toString(entity1.getContent(), "UTF8");
	    
	    Gson gson1 = new Gson(); // Or use new GsonBuilder().create();
		Type collectionType = new TypeToken<Collection<TaskPojo>>(){}.getType();
		List<TaskPojo> tareas = (List<TaskPojo>) new Gson().fromJson( json2 , collectionType);
		
		
		for(TaskPojo tarea: tareas) {
			listTareas.add(new Task(tarea.getTitulo(), tarea.getEncargado(), tarea.getComents(), tarea.getContenido(), Integer.parseInt(tarea.getPrioridad()), tarea.getFecha(), tarea.getEstado(), tarea.getVisible()));
		}
	    
	    response.close();

		return listTareas;
	}
        
        public boolean completarTarea(String username, String title, String date) {
            HttpPost httpPost = new HttpPost("https://centraldemascotas.com/aplicaciones/tasker/task_done.php");
            boolean finded = false;
            try {
                List<NameValuePair> user = new ArrayList<NameValuePair>();
                user.add(new BasicNameValuePair("encargado", username));
                user.add(new BasicNameValuePair("titulo", title));
                user.add(new BasicNameValuePair("fecha", date));
                httpPost.setEntity(new UrlEncodedFormEntity(user));
                HttpClients.createDefault().execute(httpPost);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

            return finded;
        }

    public boolean checkAdmin(String username) {
        HttpPost httpPost = new HttpPost("https://centraldemascotas.com/aplicaciones/tasker/check_user.php");
        boolean finded = false;
        try {
            List<NameValuePair> user = new ArrayList<NameValuePair>();
            user.add(new BasicNameValuePair("username", username));
            httpPost.setEntity(new UrlEncodedFormEntity(user));

            HttpResponse response1 = HttpClients.createDefault().execute(httpPost);
            HttpEntity entity1 = response1.getEntity();
            String json = IOUtils.toString(entity1.getContent(), "UTF8");
            if (json.equals("1")) {
                finded = true;
            } else {
                finded = false;
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return finded;
    }
	
	
	


}
