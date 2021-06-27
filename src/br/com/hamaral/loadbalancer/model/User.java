package br.com.hamaral.loadbalancer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * Classe responsável por definir o modelo User.
 * 
 * @author Heitor Augusto Melecardi do Amaral
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

	private Integer id;

	private Integer startAt;

	private Integer endAt;
}
