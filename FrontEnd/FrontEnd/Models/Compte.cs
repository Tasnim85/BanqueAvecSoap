namespace FrontEnd.Models
{
	public class Compte
	{
		public string Code { get; set; }
		public float Solde { get; set; }
		public Compte(string code, float solde)
		{
			Code = code;
			Solde = solde;
		}
	}

}
